export const isValidFieldNull = (data) => {
  if (!data || data.trim() === '' || data === null) {
    return false;
  }
  return true;
} 

export const isValidEmail = (email) => {
  const emailPattern = /^[a-zA-Z0-9._-]+@gmail\.com$/;
  const emailPattern2 = /^[a-zA-Z0-9._-]+@vku\.udn\.vn$/;
  if (!emailPattern.test(email) && !emailPattern2.test(email)) {
    return false;
  }
  return true;
}

export const isValidPhone = (phone) => {
  const phonePattern = /^(0[1-9])[0-9]{8}$/;
    if (!phonePattern.test(phone)) {
      return false;
  }
  return true;
}

export const isValidPassword = (password) => {
  if (password.length < 6) {
      return false;
    }
  return true;
}

export const isValidPasswordMatch = (password, confirmPassword) => {
  if (password !== confirmPassword) {
    return false;
  }
  return true
}

export const formatCurrencyVN = (amount) => {
    if (!amount && amount !== 0) return "0 đ";

    return amount.toLocaleString("vi-VN") + " đ";
};

export const formatStringToDate = (str) => {
  return str ? new Date(str).toISOString().split("T")[0] : "NULL"
}