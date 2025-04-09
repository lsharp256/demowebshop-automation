# 🛒 Demo Web Shop Automation Suite

This project contains automated UI tests for [Demo Web Shop](https://demowebshop.tricentis.com/) using **Selenium WebDriver**, **TestNG**, and **Java**. It covers common e-commerce user flows like login, product browsing, cart handling, and checkout.

## 🔧 Tech Stack

- Java 11
- Selenium WebDriver
- TestNG
- Maven
- GitHub Actions (CI/CD)
- Chrome (headless and GUI)
- Page Object Model (POM) structure

---

## ✅ Features Tested

- User login (valid and invalid scenarios)
- Product sorting and filtering (Desktops)
- Adding products to cart (including configurable products)
- Cart price validation and item removal
- Checkout process with new and existing addresses
- Order confirmation and history check

## 🚀 Running the Tests

### Prerequisites
Make sure you have the following installed:
- Java 11
- Maven
- Google Chrome (for local testing)

### Running tests locally

```bash
mvn clean test

