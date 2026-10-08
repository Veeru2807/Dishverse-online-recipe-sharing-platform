# 🍳 DishVerse – A World of Recipes
*Online Recipe Sharing Platform*

[![Java 17](https://img.shields.io/badge/Java-17%20LTS-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-green.svg)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.x-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A full-featured, enterprise-grade web application built with **Java 17, Spring Boot 3, Spring Security 6, Spring Data JPA, MySQL, and Thymeleaf**.

Designed as a complete student portfolio project suitable for **GitHub showcase, LinkedIn feature posts, resume highlights, and technical viva/interview demonstrations**.

---

## 📖 Overview

**DishVerse** is a modern, community-driven recipe-sharing ecosystem designed for home chefs, food enthusiasts, and culinary creators. The platform enables users to discover handcrafted recipes, share their own culinary creations, rate and review dishes, and bookmark favorite recipes into personalized collections.

To ensure high-quality content, DishVerse incorporates a real-time **Admin Moderation Queue** where submitted recipes undergo administrative review before being published to the public catalog.

---

## 🌟 Features

### 👤 User Features
* **Authentication & User Accounts:** Secure user registration, BCrypt password hashing, session management, and profile management.
* **Recipe Creation & Editing:** Submit rich recipes with title, description, category tags, step-by-step instructions, ingredients list, and dish photo attachments.
* **Approval Status Tracking:** View real-time approval status (`PENDING`, `APPROVED`, `REJECTED`) for submitted recipes in the user workspace.
* **Personal Bookmarks / Collections:** Save favorite community recipes into a personal collection with one-click saving and removal.
* **Ratings & Interactive Reviews:** 1 to 5-star rating system with average score calculations and textual community reviews.
* **Recipe Discovery & Search:** Search by recipe title or ingredient keywords, with quick category filter pills (Chicken, Paneer, Vegetarian, South Indian, Snacks, Chinese).

### 🛡️ Admin Features
* **Administrator Console:** Centralized dashboard displaying live platform statistics (Total Users, Pending Approvals, Approved Recipes, Total Reviews).
* **Recipe Moderation Queue:** Review pending recipe submissions with options to approve or reject content.
* **User Management:** View registered platform accounts, promote users to `ROLE_ADMIN`, demote to `ROLE_USER`, or delete accounts.
* **Platform Moderation:** Inspect all system recipes with full administrative override capabilities.

---

## 🛠️ Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 17 LTS |
| **Backend Framework** | Spring Boot 3.2.3 (Spring MVC, Spring Data JPA) |
| **Security Framework** | Spring Security 6 (BCrypt Password Hashing, Role Authorization) |
| **Database** | MySQL 8.x |
| **ORM / Persistence** | Hibernate 6.x |
| **Frontend Framework** | Thymeleaf, HTML5, CSS3, JavaScript |
| **Styling & UI Components**| Custom DishVerse CSS Design System, Bootstrap 5.3.2, FontAwesome 6.4.2 |
| **Font Family** | Google Fonts — 'Outfit' |
| **Build Tool** | Apache Maven 3.8+ |

---

## 📐 System Architecture

```
                       ┌──────────────────────────────────────────────┐
                       │  Browser / Frontend Client                   │
                       │  (Thymeleaf Templates, Bootstrap 5, CSS3)   │
                       └──────────────────────┬───────────────────────┘
                                              │ HTTP GET / POST
                                              ▼
                       ┌──────────────────────────────────────────────┐
                       │  Spring Security Filter Chain                │
                       │  (Authentication, Authorization, BCrypt)    │
                       └──────────────────────┬───────────────────────┘
                                              │ Authorized Request
                                              ▼
                       ┌──────────────────────────────────────────────┐
                       │  Controller Layer                            │
                       │  (Request Mapping, Form Validation, DTOs)    │
                       └──────────────────────┬───────────────────────┘
                                              │ Service Calls
                                              ▼
                       ┌──────────────────────────────────────────────┐
                       │  Service Layer                               │
                       │  (Business Logic, Workflow & Moderation)      │
                       └──────────────────────┬───────────────────────┘
                                              │ JPA Queries
                                              ▼
                       ┌──────────────────────────────────────────────┐
                       │  Repository Layer & Hibernate ORM            │
                       │  (Spring Data JPA Interfaces)                │
                       └──────────────────────┬───────────────────────┘
                                              │ SQL
                                              ▼
                       ┌──────────────────────────────────────────────┐
                       │  MySQL 8.x Database (`recipe_sharing_db`)    │
                       └──────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
d:/Java Project/
├── src/main/java/com/recipeshare/
│   ├── RecipeSharingApplication.java      # Main Spring Boot Entry Point
│   ├── config/                            # SecurityConfig, WebMvcConfig, DataInitializer
│   ├── controller/                        # Auth, Home, Recipe, Collection, Review, Admin, Profile Controllers
│   ├── dto/                               # UserRegistrationDto, RecipeDto, ReviewDto, UserProfileDto
│   ├── entity/                            # User, Recipe, Rating, Review, RecipeCollection, SystemSetting
│   ├── enums/                             # Role (ROLE_USER, ROLE_ADMIN), RecipeStatus (PENDING, APPROVED, REJECTED)
│   ├── exception/                         # GlobalExceptionHandler, ResourceNotFoundException
│   ├── repository/                        # Spring Data JPA Repositories
│   ├── service/                           # User, Recipe, Review, Collection, Admin Service Implementations
│   └── util/                              # FileUploadUtil, SecurityUtils
│
├── src/main/resources/
│   ├── static/css/style.css               # Centralized DishVerse Design System & CSS Variables
│   ├── templates/                         # Thymeleaf HTML Templates
│   │   ├── admin/                         # dashboard.html, recipes.html, users.html
│   │   ├── auth/                          # login.html, register.html
│   │   ├── collection/                    # list.html
│   │   ├── error/                         # 403.html, 404.html, 500.html
│   │   ├── fragments/                     # common.html (Navbar & Footer Fragments)
│   │   ├── recipe/                        # list.html, details.html, create.html, edit.html, my-recipes.html
│   │   └── user/                          # dashboard.html, profile.html
│   └── application.properties             # Environment Variable Configurable Properties
│
├── uploads/recipes/                       # Local Recipe Image Storage (15 Seeded Food Images)
├── pom.xml                                # Maven Project Dependencies
└── README.md                              # Public Repository Documentation
```

---

## 🗄️ Database Schema & Entities

Database Name: **`recipe_sharing_db`**

1. **`users`**: `id` (PK), `name`, `email` (UNIQUE), `password` (BCrypt Hash), `role` (`ROLE_USER` / `ROLE_ADMIN`), `created_at`, `updated_at`.
2. **`recipes`**: `id` (PK), `user_id` (FK), `title`, `description`, `ingredients`, `instructions`, `image_url`, `status` (`PENDING` / `APPROVED` / `REJECTED`), `created_at`, `updated_at`.
3. **`ratings`**: `id` (PK), `recipe_id` (FK), `user_id` (FK), `rating` (1–5), `created_at`. *Constraint: Unique composite index `(recipe_id, user_id)`.*
4. **`reviews`**: `id` (PK), `recipe_id` (FK), `user_id` (FK), `comment`, `created_at`, `updated_at`.
5. **`recipe_collections`**: `id` (PK), `user_id` (FK), `recipe_id` (FK), `created_at`. *Constraint: Unique composite index `(user_id, recipe_id)`.*
6. **`system_settings`**: `id` (PK), `setting_key` (UNIQUE), `setting_value`.

---

## 🔒 Security & Role Authorization

* **Password Protection:** User passwords are encrypted using `BCryptPasswordEncoder`.
* **Access Control:**
  - `ROLE_ADMIN`: Full access to Administrator Console (`/admin/**`), user role management, and recipe approval/rejection moderation.
  - `ROLE_USER`: Access to user workspace (`/dashboard`), recipe submission, editing own recipes, bookmarking, and reviews.
  - `Anonymous Public`: Ability to browse approved public recipe catalog (`/recipes`), search by title/ingredient, and view recipe details (`/recipes/details/{id}`).

---

## 🔄 Recipe Submission & Moderation Workflow

```
 USER submits a new recipe
           │
           ▼
 Status set to PENDING
 (Visible only in User Workspace)
           │
           ▼
 ADMIN inspects Moderation Queue
           │
 ┌─────────┴─────────┐
 ▼                   ▼
APPROVE            REJECT
 │                   │
 ▼                   ▼
Status: APPROVED   Status: REJECTED
Published to       Hidden from
Public Catalog     Public Catalog
```

---

## 🖼️ Screenshots Section

*(Add screenshots of your local execution here for your portfolio showcase)*

| Home Page Hero & Catalog | Recipe Detail View |
| :---: | :---: |
| *(Insert Home Page Screenshot)* | *(Insert Recipe Details Screenshot)* |

| User Dashboard | Admin Moderation Console |
| :---: | :---: |
| *(Insert User Dashboard Screenshot)* | *(Insert Admin Console Screenshot)* |

---

## ⚙️ Installation & Setup

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher
* **MySQL Database Server:** Version 8.x running on port `3306`
* **Apache Maven:** Version 3.8 or higher

### Environment Configuration

Configure your database connection using environment variables prior to running the application.

#### Linux / macOS
```bash
export DB_URL="jdbc:mysql://localhost:3306/recipe_sharing_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export DB_USERNAME="root"
export DB_PASSWORD="<your_local_mysql_password>"
```

#### Windows (PowerShell)
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/recipe_sharing_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<your_local_mysql_password>"
```

| Variable | Description | Required | Default Fallback |
| :--- | :--- | :--- | :--- |
| `DB_URL` | MySQL Connection JDBC URL | Optional | `jdbc:mysql://localhost:3306/recipe_sharing_db...` |
| `DB_USERNAME` | MySQL Username | Optional | `root` |
| `DB_PASSWORD` | MySQL Password | **Required** | *(Set via local environment variable)* |
| `PORT` | Application HTTP Port | Optional | `8080` |
| `ADMIN_EMAIL` | Administrator Account Email | Optional | *(Configured in local environment)* |
| `ADMIN_PASSWORD` | Administrator Account Password | Optional | *(Configured in local environment)* |

### Step-by-Step Execution

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/dishverse.git
   cd dishverse
   ```

2. **Start MySQL Database Server:**
   Ensure MySQL service is running on `localhost:3306`. The database `recipe_sharing_db` will be created automatically on startup if it does not exist.

3. **Build & Compile the Project:**
   ```bash
   mvn clean compile
   ```

4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access DishVerse Web Interface:**
   Open your browser and navigate to: `http://localhost:8080`

---

## 🔑 Demo Account Setup

Administrator accounts can be created locally through user registration and role promotion in the database, or configured via local environment variables (`ADMIN_EMAIL` and `ADMIN_PASSWORD`). Refer to your local environment configuration or `DataInitializer.java` to set up initial admin credentials for testing.

---

## 🚀 Future Enhancements

* **Ingredient Purchasing Calculator:** Export ingredient list to shopping list.
* **Nutrition Breakdown API:** Integrate Third-Party Food API for calorie & macro estimation.
* **Social Sharing:** One-click share to WhatsApp, LinkedIn, and X.
* **Video Tutorials:** Embed YouTube/Vimeo video player for step-by-step cooking videos.

---

## 👨‍💻 Author

**DishVerse Team**  
*Built for College Final Project Submission, Portfolio Demonstration, and GitHub Showcase.*
