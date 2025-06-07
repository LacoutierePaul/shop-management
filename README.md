# 🛒 Shop Management Application

[![Java](https://img.shields.io/badge/Language-Java-red?style=flat-square)](https://www.java.com/)
[![JavaFX](https://img.shields.io/badge/GUI-JavaFX-3c99dc?style=flat-square&logo=java)](https://openjfx.io/)
[![SQLite](https://img.shields.io/badge/Database-SQLite-003B57?style=flat-square)](https://www.sqlite.org/index.html)

---

## 📦 Overview

This is a **Shop Management Application** built with **JavaFX** for the GUI and **SQLite** as the database.  
It supports product management, stock tracking, sales and purchases, discounts, and financial overview in a user-friendly interface.


---

## 🚀 Features

- ✅ Display products by category with stock levels  
- ✅ Add, modify, and delete products with validation  
- ✅ Track capital, total incomes, and total costs  
- ✅ Sell and purchase products, automatically updating stock and finances  
- ✅ Apply and remove discounts with immediate price updates  
- ✅ Data validation with error messages for incorrect input or invalid operations  

---

## 🛠 Usage

1. **Login page:**  
   Connect to your SQLite database or create a new one with the provided script on first launch.
   
![image](https://github.com/user-attachments/assets/28ad79e0-3349-4332-a415-9b766f824eaa)

2. **Main page:**  
   Manage your shop’s inventory, perform sales/purchases, and monitor capital, incomes, and costs.
   
![image](https://github.com/user-attachments/assets/5efb7f5a-5063-484c-974f-ea0795f7e404)

---

## 📋 Validation & Error Handling

The app validates input and handles errors such as:  
- Missing or incorrect product information  
- Operations without selecting a product  
- Selling more stock than available  
- Invalid sizes for clothes/shoes

---

## 💻 Example Workflow

| Action              | Effect                                                      |
|---------------------|-------------------------------------------------------------|
| Add new product     | Adds item to inventory after confirmation                    |
| Sell product         | Decreases stock, increases incomes and capital              |
| Buy product          | Increases stock, increases costs, decreases capital         |
| Apply discount       | Updates product unit price and affects sales accordingly    |

---

## 👥 Authors

- Florian Potonne  
- Paul Lacoutiere  


