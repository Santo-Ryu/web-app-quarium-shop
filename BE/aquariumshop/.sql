-- create database aquarium_shop;
-- use aquarium_shop;

-- nếu ng dùng vừa đăng ký tài khoản thì image mặc định sẽ đc cập nhật
CREATE TABLE user_images (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_status (
	id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(50) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO order_status (status_name)
VALUES	('Đang xử lý'),
		('Đang vận chuyển'),
		('Đã hoàn thành'),
		('Bị hủy');

CREATE TABLE admins(
	id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender TINYINT(1) NOT NULL DEFAULT 2, -- | 0: male | 1: female | 2: orther |
    birth_date DATE NULL,
    phone VARCHAR(11) NULL,
    verify_email TINYINT(1) NOT NULL DEFAULT 0, -- | 0: not verify | 1: verify |
    verify_at TIMESTAMP NULL,
    image_id INT NOT NULL,
    role TINYINT(1) NOT NULL DEFAULT 1, -- | 0: khách hàng | 1: admin |

    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_admins_image_id FOREIGN KEY (image_id) REFERENCES user_images(id)
);

CREATE TABLE customers (
	id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender TINYINT(1) NOT NULL DEFAULT 2, -- | 0: male | 1: female | 2: orther |
    birth_date DATE NULL,
    phone VARCHAR(11) NULL,
    address VARCHAR(255) NULL,
    verify_email TINYINT(1) NOT NULL DEFAULT 0, -- | 0: not verify | 1: verify |
    verify_at TIMESTAMP NULL,
    image_id INT NOT NULL,
    role TINYINT(1) NOT NULL DEFAULT 0,
    last_login TIMESTAMP NULL,

    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_customers_image_id FOREIGN KEY (image_id) REFERENCES user_images(id)
);

CREATE TABLE categories (
	id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(255) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE products (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    category_id INT NOT NULL,
    description TEXT NOT NULL,
    quantity INT DEFAULT 0, -- số lượng tồn kho
    price INT DEFAULT 0,
    discount_percentage INT DEFAULT 0,
    discount_start_date TIMESTAMP,
    discount_end_date TIMESTAMP,
    rating DECIMAL(3,1) DEFAULT 0.0, -- đánh giá dựa vào rating ở comments
    sales_count INT DEFAULT 0, -- lượt bán

    /*	| 0: đang bán | 1: dừng bán |
		Khi muốn dừng bán 1 sản phẩm nào đó bất kỳ thay vì xóa nó khỏi database
	*/
    is_active TINYINT(1) DEFAULT 0,

    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

	/* 	Tự động xóa các sản phẩm khi category bị xóa
        Tuy nhiên với các sản phẩm trong đơn hàng thì sẽ có logic xử lý riêng để xóa các đơn hàng nằm 'Đang xử lý' or 'Đang vận chuyển'
	*/
    CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE product_images (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    product_id INT NOT NULL, -- nhiều ảnh có thể là cùng của một sản phẩm

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_image_id FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- ngày đặt hàng
    status_id INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_orders_status_id FOREIGN KEY (status_id) REFERENCES order_status(id)
);

CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price INT NOT NULL DEFAULT 0,
    discount_percent INT DEFAULT 0,
    subtotal INT DEFAULT 0,

    CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE comments(
	id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    content TEXT NOT NULL,
    rating TINYINT(1) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_comments_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE verify_email (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE password_reset_token (
	id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
