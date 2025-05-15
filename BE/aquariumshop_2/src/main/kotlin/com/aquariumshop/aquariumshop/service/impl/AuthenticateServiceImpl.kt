package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.AccountCreateRequest
import com.aquariumshop.aquariumshop.dto.request.AuthenticateRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyEmailRequest
import com.aquariumshop.aquariumshop.dto.request.VerifyPasswordResetRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAccountResponse
import com.aquariumshop.aquariumshop.dto.response.AdminAuthenticateResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAccountResponse
import com.aquariumshop.aquariumshop.dto.response.CustomerAuthenticateResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.enums.EmailPurpose
import com.aquariumshop.aquariumshop.enums.Role
import com.aquariumshop.aquariumshop.enums.VerifyEmailType
import com.aquariumshop.aquariumshop.mapper.AdminMapper
import com.aquariumshop.aquariumshop.mapper.CustomerMapper
import com.aquariumshop.aquariumshop.model.entity.Admin
import com.aquariumshop.aquariumshop.model.entity.Customer
import com.aquariumshop.aquariumshop.model.entity.PasswordResetToken
import com.aquariumshop.aquariumshop.model.entity.UserImage
import com.aquariumshop.aquariumshop.model.entity.VerifyEmail
import com.aquariumshop.aquariumshop.repository.AdminRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.PasswordResetTokenRepository
import com.aquariumshop.aquariumshop.repository.UserImageRepository
import com.aquariumshop.aquariumshop.repository.VerifyEmailRepository
import com.aquariumshop.aquariumshop.service.AuthenticateService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.security.SecureRandom
import kotlin.random.Random
import java.time.Duration
import java.time.LocalDateTime
import java.util.Base64

@RequiredArgsConstructor
@Service
class AuthenticateServiceImpl(
    private val encryptServiceImpl: EncryptServiceImpl,
    private val customerRepository: CustomerRepository,
    private val adminRepository: AdminRepository,
    private val emailServiceImpl: EmailServiceImpl,
    private val userImageRepository: UserImageRepository,
    private val verifyEmailRepository: VerifyEmailRepository,
    private val jwtServiceImpl: JWTServiceImpl,
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val customerMapper: CustomerMapper,
    private val adminMapper: AdminMapper
): AuthenticateService {

    override fun adminRegister(request: AccountCreateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        val email = encryptServiceImpl.decrypt(request.email)
        val password = request.password
        val role = Role.valueOf(encryptServiceImpl.decrypt(request.role))

        if (role == Role.ADMIN) {
            val existingAdmin = adminRepository.findByEmail(email)
            existingAdmin?.let {
                return ResponseFactory.badRequest("Email đã tồn tại!")
            }

//            Tạo đối tượng
            val userImage = UserImage()
            userImage.name = null
            userImageRepository.save(userImage)

            val newAdmin = Admin()
            newAdmin.email = email
            newAdmin.name = generateRandomName(role)
            newAdmin.password = password
            newAdmin.image = userImage
            adminRepository.save(newAdmin)

//            Tạo và lưu token
            val tokenSearch = verifyEmailRepository.findByEmail(newAdmin.email!!)
            if ( tokenSearch != null) {
                verifyEmailRepository.deleteByEmail(newAdmin.email!!)
            }
            val verifyToken = emailServiceImpl.generateToken()

            val verifyEmail = VerifyEmail()
            verifyEmail.email = newAdmin.email
            verifyEmail.token = verifyToken
            verifyEmailRepository.save(verifyEmail)

//            Gửi mail
            emailServiceImpl.sendEmail(
                name = newAdmin.name!!,
                token = verifyToken,
                email = newAdmin.email!!,
                purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                role = role
            )
        }else return ResponseFactory.badRequest("Role khồng phù hợp!")

        val response = AdminAuthenticateResponse(
            isAuthenticated = true,
            token = null,
//            account = null,
        )
        return ResponseFactory.success(response, "Tạo tài khoản thành công! Kiểm tra email để xác thực tài khoản!")
    }

    override fun adminLogin(request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return try {
            val email = encryptServiceImpl.decrypt(request.email)
            val password = request.password
            val role = Role.valueOf(encryptServiceImpl.decrypt(request.role.toString()))

            println("ROLE $role")

            if (role == Role.ADMIN) {
                val admin = adminRepository.findByEmail(email)
                    ?: return ResponseFactory.badRequest("Email không tồn tại!")

                if (admin.password != password) {
                    return ResponseFactory.badRequest("Mật khẩu không đúng!")
                }

                if (admin.verifyEmail === VerifyEmailType.UNVERIFIED) {
                    val tokenSearch = verifyEmailRepository.findByEmail(admin.email!!)
                    if ( tokenSearch != null) {
                        verifyEmailRepository.deleteByEmail(admin.email!!)
                    }
                    val token = emailServiceImpl.generateToken()
                    val verifyEmail = VerifyEmail()
                    verifyEmail.token = token
                    verifyEmail.email = email
                    verifyEmailRepository.save(verifyEmail)

                    emailServiceImpl.sendEmail(
                        name = admin.name!!,
                        token = token,
                        email = admin.email!!,
                        purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                        role = Role.ADMIN
                    )
                    return ResponseFactory.unauthorized("Email chưa được xác thưc! Kiểm tra email để xác thực tài khoản!")
                }

                val adminResponse = adminMapper.toResponse(adminRepository.findByEmail(email)!!)
                println("ADMIN_RESPONSE: $adminResponse")
                val account = AdminAccountResponse(
                    admin = adminResponse
                )

                val jwtToken = jwtServiceImpl.generateAdminToken(admin)
                val response = AdminAuthenticateResponse(
                    isAuthenticated = true,
                    token = jwtToken,
                    account = account
                )
                ResponseFactory.success(response, "Đăng nhập thành công!")
            } else {
                println("ROLE không phù hợp!")
                ResponseFactory.badRequest("Role không phù hợp")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseFactory.badRequest("Đăng nhập thất bại: ${e.message}")
        }
    }

    override fun adminPasswordReset(request: AuthenticateRequest): ResponseEntity<APIResponse<AdminAuthenticateResponse>> {
        return try {
            val email = encryptServiceImpl.decrypt(request.email)
            val role = Role.valueOf(encryptServiceImpl.decrypt(request.role))

            println("EMAIL: $email")
            println("EMAIL: $role")

            if (role === Role.ADMIN) {
                val admin = adminRepository.findByEmail(email)
                    ?: return ResponseFactory.badRequest("Email không tồn tại!")

                val searchToken = passwordResetTokenRepository.findByEmail(email)
                if (searchToken != null) {
                    passwordResetTokenRepository.deleteByEmail(email)
                }
                val token = generateToken()
                val passwordResetToken = PasswordResetToken()
                passwordResetToken.email = admin.email
                passwordResetToken.token = token
                passwordResetTokenRepository.save(passwordResetToken)

                emailServiceImpl.sendEmail(
                    name = admin.name!!,
                    email = admin.email!!,
                    token = token,
                    purpose = EmailPurpose.PASSWORD_RESET,
                    role = Role.ADMIN
                )
                val response = AdminAuthenticateResponse(null, null)
                ResponseFactory.success(response, "Gửi mật khẩu mới thành công! Vui lòng kiểm tra email!")
            }else {
                ResponseFactory.badRequest("Role không hợp lệ!")
            }

        }catch (e: Exception) {
            e.printStackTrace()
            ResponseFactory.badRequest("Lấy lại mật khẩu mới thất bại!")
        }
    }

    override fun customerRegister(request: AccountCreateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        val email = encryptServiceImpl.decrypt(request.email)
        val password = request.password
        val role = Role.valueOf(encryptServiceImpl.decrypt(request.role))

        if (role == Role.CUSTOMER) {
            println("CUSTOMER REGISTER: $email")
            val existingCustomer = customerRepository.findByEmail(email)
            existingCustomer?.let {
                return ResponseFactory.badRequest("Email đã tồn tại!")
            }

//            Tạo đối tượng
            val userImage = UserImage()
            userImage.name = null
            userImageRepository.save(userImage)

            val newCustomer = Customer()
            newCustomer.email = email
            newCustomer.name = generateRandomName(role)
            newCustomer.password = password
            newCustomer.image = userImage
            customerRepository.save(newCustomer)

//            Tạo và lưu token
            val tokenSearch = verifyEmailRepository.findByEmail(newCustomer.email!!)
            if ( tokenSearch != null) {
                verifyEmailRepository.deleteByEmail(newCustomer.email!!)
            }
            val verifyToken = emailServiceImpl.generateToken()

            val verifyEmail = VerifyEmail()
            verifyEmail.email = newCustomer.email
            verifyEmail.token = verifyToken
            verifyEmailRepository.save(verifyEmail)

//            Gửi mail
            emailServiceImpl.sendEmail(
                name = newCustomer.name!!,
                token = verifyToken,
                email = newCustomer.email!!,
                purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                role = role
            )
        }else return ResponseFactory.badRequest("Role khồng phù hợp!")

        val response = CustomerAuthenticateResponse(
            isAuthenticated = true,
            token = null,
//            account = null,
        )
        return ResponseFactory.success(response, "Tạo tài khoản thành công! Kiểm tra email để xác thực tài khoản!")
    }

    override fun customerLogin(request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return try {
            val email = encryptServiceImpl.decrypt(request.email)
            val password = request.password
            val role = Role.valueOf(encryptServiceImpl.decrypt(request.role.toString()))

            if (role == Role.CUSTOMER) {
                val customer = customerRepository.findByEmail(email)
                    ?: return ResponseFactory.badRequest("Email không tồn tại!")

                if (customer.password != password) {
                    return ResponseFactory.badRequest("Mật khẩu không đúng!")
                }

                if (customer.verifyEmail === VerifyEmailType.UNVERIFIED) {
                    val tokenSearch = verifyEmailRepository.findByEmail(customer.email!!)
                    if ( tokenSearch != null) {
                        verifyEmailRepository.deleteByEmail(customer.email!!)
                    }
                    val token = emailServiceImpl.generateToken()
                    val verifyEmail = VerifyEmail()
                    verifyEmail.token = token
                    verifyEmail.email = email
                    verifyEmailRepository.save(verifyEmail)

                    emailServiceImpl.sendEmail(
                        name = customer.name!!,
                        token = token,
                        email = customer.email!!,
                        purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                        role = Role.CUSTOMER
                    )
                    return ResponseFactory.unauthorized("Email chưa được xác thưc! Kiểm tra email để xác thực tài khoản!")
                }

                val customerResponse = customerMapper.toResponse(customerRepository.findByEmail(email)!!)
                val account = CustomerAccountResponse(
                    customer = customerResponse
                )

                val jwtToken = jwtServiceImpl.generateCustomerToken(customer)
                val response = CustomerAuthenticateResponse(
                    isAuthenticated = true,
                    token = jwtToken,
                    account = account
                )
                ResponseFactory.success(response, "Đăng nhập thành công!")
            } else {
                ResponseFactory.badRequest("Role không phù hợp")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseFactory.badRequest("Đăng nhập thất bại: ${e.message}")
        }
    }

    override fun customerPasswordReset(request: AuthenticateRequest): ResponseEntity<APIResponse<CustomerAuthenticateResponse>> {
        return try {
            val email = encryptServiceImpl.decrypt(request.email)
            val role = Role.valueOf(encryptServiceImpl.decrypt(request.role))

            println("EMAIL: $email")
            println("EMAIL: $role")

            if (role === Role.CUSTOMER) {
                val customer = customerRepository.findByEmail(email)
                    ?: return ResponseFactory.badRequest("Email không tồn tại!")

                val searchToken = passwordResetTokenRepository.findByEmail(email)
                if (searchToken != null) {
                    passwordResetTokenRepository.deleteByEmail(email)
                }
                val token = generateToken()
                val passwordResetToken = PasswordResetToken()
                passwordResetToken.email = customer.email
                passwordResetToken.token = token
                passwordResetTokenRepository.save(passwordResetToken)

                emailServiceImpl.sendEmail(
                    name = customer.name!!,
                    email = customer.email!!,
                    token = token,
                    purpose = EmailPurpose.PASSWORD_RESET,
                    role = Role.CUSTOMER
                )
                val response = CustomerAuthenticateResponse(null, null)
                ResponseFactory.success(response, "Gửi mật khẩu mới thành công! Vui lòng kiểm tra email!")
            }else {
                ResponseFactory.badRequest("Role không hợp lệ!")
            }

        }catch (e: Exception) {
            e.printStackTrace()
            ResponseFactory.badRequest("Lấy lại mật khẩu mới thất bại!")
        }
    }

    override fun handleVerifyPasswordReset(request: VerifyPasswordResetRequest): ModelAndView {
        val modelAndView = ModelAndView("verify_page")
        val email = request.email
        val token = request.token
        val password = request.password
        val role = Role.valueOf(request.role)

        if (role === Role.CUSTOMER) {
            val customer = customerRepository.findByEmail(email)
            val passwordResetToken = passwordResetTokenRepository.findByToken(token)

            if (token != null && customer != null) {
                customer.password = encryptServiceImpl.hashPassword(password)
                customerRepository.save(customer)
                passwordResetTokenRepository.delete(passwordResetToken!!)
            }
        }else if (role === Role.ADMIN) {
            val admin = adminRepository.findByEmail(email)
            val passwordResetToken = passwordResetTokenRepository.findByToken(token)

            if (token != null && admin != null) {
                admin.password = encryptServiceImpl.hashPassword(password)
                adminRepository.save(admin)
                passwordResetTokenRepository.delete(passwordResetToken!!)
            }
        }else println("ROLE KHÔNG HỢP LỆ!")

        return modelAndView
    }

    override fun handleVerifyEmail(request: VerifyEmailRequest): ModelAndView {
        val token = request.token
        val email = request.email
        val role = Role.valueOf(value = request.role)

        val EXPIRATION_MINUTES = 5L
        val modelAndView = ModelAndView("verify_page")

        val verifyEmail = verifyEmailRepository.findByToken(token) ?: throw IllegalArgumentException("Token không tồn tại!")
        val duration = Duration.between(verifyEmail.createdAt, LocalDateTime.now())
        if (duration.toMinutes() <= EXPIRATION_MINUTES && verifyEmail != null ) { // token còn sống
            when (role) {
                Role.CUSTOMER -> {
                    var customer = customerRepository.findByEmail(email) ?: throw IllegalArgumentException("Email khách hàng không tồn tại!")
                    customer.verifyEmail = VerifyEmailType.VERIFIED
                    customer.verifyAt = LocalDateTime.now()
                    customerRepository.save(customer)
                    verifyEmailRepository.delete(verifyEmail)
                }
                Role.ADMIN -> {
                    val admin = adminRepository.findByEmail(email) ?: throw IllegalArgumentException("Email quản trị viên không tồn tại!")
                    admin.verifyEmail = VerifyEmailType.VERIFIED
                    admin.verifyAt = LocalDateTime.now()
                    adminRepository.save(admin)
                    verifyEmailRepository.delete(verifyEmail)
                }
            }
            modelAndView.addObject("status", "success")
        }else {
            verifyEmailRepository.deleteByEmail(email)
            when (role) {
                Role.CUSTOMER -> {
                    var customer = customerRepository.findByEmail(email) ?: throw IllegalArgumentException("Email khách hàng không tồn tại!")
                    var token = emailServiceImpl.generateToken()
                    emailServiceImpl.sendEmail(
                        name = customer.name!!,
                        token = token,
                        email = customer.email!!,
                        purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                        role = Role.CUSTOMER
                    )
                }
                Role.ADMIN -> {
                    var admin = adminRepository.findByEmail(email) ?: throw IllegalArgumentException("Email quản trị viên không tồn tại!")
                    var token = emailServiceImpl.generateToken()
                    emailServiceImpl.sendEmail(
                        name = admin.name!!,
                        token = token,
                        email = admin.email!!,
                        purpose = EmailPurpose.ACCOUNT_VERIFICATION,
                        role = Role.ADMIN
                    )
                }
            }
            modelAndView.addObject("status", "expired")
        }

        return modelAndView
    }

    override fun generateRandomName(role: Role): String {
        val randomName = (1..6)
            .map { Random.nextInt(0, 10) }
            .joinToString("")
        val rolePrefix = when (role) {
            Role.CUSTOMER -> "CUSTOMER"
            Role.ADMIN -> "ADMIN"
        }

        return "$rolePrefix$randomName"
    }

    override fun generateToken(): String {
        val secureRandom = SecureRandom()
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }
}