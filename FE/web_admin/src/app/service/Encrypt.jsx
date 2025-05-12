/* eslint-disable no-unused-vars */
import CryptoJS, { enc } from "crypto-js";

// MÃ HÓA AES + THUẬT TOÁN GIẤU KEY VÀO CHUỖI MÃ HÓA
const generateAESKey = () => {
    return CryptoJS.lib.WordArray.random(32); // tạo AES key ngẫu nhiên
};

const getIVCode = () => {
    return CryptoJS.lib.WordArray.random(16); // tạo iv code
}

const timeStamp = () => {
    const now = new Date();
    return [now.getHours(), now.getMinutes(), now.getSeconds()];
}

// Chuyển AESKey => new AESKey để mã hóa dữ liệu
const tranformeKey = (AESKey, iv, timeStr) => {
    const timeShift = (timeStr.slice(0, 2) * timeStr.slice(2, 4) * timeStr.slice(4, 6));

    // Sử dụng PBKDF2 để tạo AES key mới
    const iterations = Math.max(1000, Math.min(10000, timeShift * 10)); // số vòng lặp
    const keySize = 256/32; // kích thước khóa 256 bit
    const newAESKey = CryptoJS.PBKDF2(
        AESKey.toString(CryptoJS.enc.Base64),
        iv,
        {keySize: keySize, iterations: iterations}
    );
    
    return {
        newAESKey: newAESKey,
        iv: iv,
        timeStr: timeStr
    };
}

// Cấu trúc lại chuỗi mã hóa
const mixEncodedData = (AESKey, encryptData, iv, timeStr) => {
    
    // chuyển dữ liệu về base64
    const AESKeyBase64 = AESKey.toString(CryptoJS.enc.Base64); // 44 ký tự
    const ivBase64 = iv.toString(CryptoJS.enc.Base64); // 24 ký tự
    const timeStrBase64 = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(timeStr.toString())); // 8 ký tự
    const encryptDataBase64 = encryptData.toString(CryptoJS.enc.Base64); 

    const AESKeyParts = [
        AESKeyBase64.slice(0, 11),
        AESKeyBase64.slice(11, 22),
        AESKeyBase64.slice(22, 33),
        AESKeyBase64.slice(33, 44)
    ];
    const ivParts = [
        ivBase64.slice(0, 6),
        ivBase64.slice(6, 12),
        ivBase64.slice(12, 18),
        ivBase64.slice(18, 24)
    ];

    let mixedParts = '';
    
    // Trộn xen kẽ
    for (let i = 0; i < 4; i++) {
        mixedParts += AESKeyParts[i] + ivParts[i];
    }

    // cấu trúc lại chuỗi mã hóa hoàn chỉnh
    const mixEncodeData =  encryptDataBase64.slice(0, 8) // phần đầu của chuỗi mã hóa
                          + mixedParts.slice(0, 32)     // Lấy một phần của mixedParts
                          + timeStrBase64               // timeStr
                          + mixedParts.slice(32)        // Lấy phần còn lại của mixedParts
                          + encryptDataBase64.slice(8); // phần còn lại của chuỗi mã hóa
                          
    return mixEncodeData;
}

// Mã hóa dữ liệu bằng AESKey mới
export const encrypt = (data) => {
    const AESKey = generateAESKey();
    const ivCode = getIVCode();
    const timestamp = timeStamp();
    const timeStr_ = timestamp.map((unit) => String(unit).padStart(2, '0')).join('');

    // tạo newAESKey để mã hóa, lấy iv, timeShift
    const {newAESKey, iv, timeStr} = tranformeKey(AESKey, ivCode, timeStr_);

    // mã hóa dữ liệu = newAESKey
    const encryptData = CryptoJS.AES.encrypt(data, newAESKey, {iv: iv});

    // trả về chuỗi mã hóa đã được cấu trúc lại 
    // => chuỗi mã hóa mới gồm AESKey + encryptData + iv + timeStr được xáo trộn 
    return mixEncodedData(AESKey, encryptData.ciphertext, iv, timeStr);
}

// Giải mã dữ liệu
export const decrypt = (encodedData) => {
    // tách chuỗi
    const encryptDataBase64 = encodedData.slice(0, 8) + encodedData.slice(84);
    const mixedPartsBase64 = encodedData.slice(8, 40) + encodedData.slice(48, 84);
    const timeStrBase64 = encodedData.slice(40, 48);
    const timeStr_ = CryptoJS.enc.Base64.parse(timeStrBase64).toString(CryptoJS.enc.Utf8);

    // tách AESKey và iv từ mixed
    const {AESKey, ivCode} = parseMixedParts(mixedPartsBase64);

    // tái tạo aesKey từ PBKDF2
    const {newAESKey, iv, timeStr} = tranformeKey(AESKey, ivCode, timeStr_);

    const decryptText =  CryptoJS.AES.decrypt(
        {ciphertext: CryptoJS.enc.Base64.parse(encryptDataBase64)},
        newAESKey,
        {iv: iv}
    )

    return decryptText.toString(CryptoJS.enc.Utf8);
};

const parseMixedParts = (mixedParts) => {
    const AESKeyParts = [];
    const ivParts = [];

    for (let i = 0; i < 4; i++) {
        AESKeyParts.push(mixedParts.slice(i * 17, i * 17 + 11));
        ivParts.push(mixedParts.slice(i * 17 + 11, (i + 1) * 17));
    }

    // Tạo lại AESKey và ivCode
    let AESKey = AESKeyParts.join('');
    let ivCode = ivParts.join('');

    return { AESKey: CryptoJS.enc.Base64.parse(AESKey)
            , ivCode: CryptoJS.enc.Base64.parse(ivCode)};
};

// MÃ HÓA 1 CHIỀU
export const hashPassword = (password) => {
    return CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
}