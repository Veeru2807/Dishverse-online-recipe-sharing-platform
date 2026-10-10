# 🍳 DishVerse – Online Recipe Sharing Platform

[![Java 17](https://img.shields.io/badge/Java-17%20LTS-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-green.svg)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.x-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**DishVerse** is a full-featured, enterprise-grade web application built with **Java 17, Spring Boot 3, Spring Security 6, Spring Data JPA, MySQL 8.x, Thymeleaf, and Jakarta Servlets**. The platform provides a modern, community-driven recipe sharing ecosystem equipped with content moderation workflows, personal bookmark collections, interactive ratings and reviews, BCrypt-secured user authentication, and high-performance direct JDBC JSON data export endpoints.

---

## 📋 Table of Contents
1. [Project Overview & Objectives](#1-project-overview--objectives)
2. [Problem Statement](#2-problem-statement)
3. [Implemented Features by Role](#3-implemented-features-by-role)
4. [Technology Stack & Prerequisites](#4-technology-stack--prerequisites)
5. [System Architecture & Layered Design](#5-system-architecture--layered-design)
6. [Actual Project Directory Structure](#6-actual-project-directory-structure)
7. [Database Setup & Entity Relationships](#7-database-setup--entity-relationships)
8. [Environment Variables Configuration](#8-environment-variables-configuration)
9. [Windows PowerShell Setup & Execution Guide](#9-windows-powershell-setup--execution-guide)
10. [Local Application Access Guide](#10-local-application-access-guide)
11. [Authentication, Role-Based Authorization & Password Hashing](#11-authentication-role-based-authorization--password-hashing)
12. [JDBC Integration & Recipe Export Servlet](#12-jdbc-integration--recipe-export-servlet)
13. [Build & Testing Commands](#13-build--testing-commands)
14. [Troubleshooting Guide](#14-troubleshooting-guide)
15. [GitHub Repository Link](#15-github-repository-link)
16. [Application Screenshots](#16-application-screenshots)
17. [Limitations & Future Scope](#17-limitations--future-scope)
18. [Author Information](#18-author-information)

---

## 1. Project Overview & Objectives.

### Overview.
DishVerse is designed to connect home chefs, culinary enthusiasts, and food lovers. Users can explore curated recipes, publish their own culinary creations, save recipes into personal bookmark collections, and rate or review community dishes. To maintain catalog quality, submitted recipes undergo administrative review via a dedicated **Admin Moderation Queue** before being published to the public interface.

### Core Objectives.
* **Maintain Content Quality:** Implement a 3-stage recipe status lifecycle (`PENDING` → `APPROVED` / `REJECTED`) managed by platform administrators.
* **Role-Based Access Control:** Secure user workflows and administrative management using Spring Security 6 authorization rules.
* **Interactive Community Engagement:** Enable users to leave 1-to-5 star ratings and textual reviews with automatic average rating computation.
* **Hybrid Data Access Demonstrations:** Integrate standard Spring Data JPA repository abstractions alongside native `java.sql` (JDBC) components and Jakarta Servlets to demonstrate dual-tier database access patterns.

---

## 2. Problem Statement.

Many online recipe platforms suffer from:
1. **Unmoderated Content Submissions:** Lack of review workflows leads to incomplete, spammy, or inaccurate recipes in public catalogs.
2. **Weak Access Control & Insecure Authentication:** Plaintext password storage or poorly enforced role separation compromises user privacy and system security.
3. **Monolithic Data Access Overhead:** Relying exclusively on heavy ORM mapping for lightweight high-volume data exports can introduce unnecessary memory overhead and query latency.
4. **Poor UI/UX Design & Fragmented User Workflow:** Difficult navigation between recipe submission, user collection management, and admin oversight.

**DishVerse resolves these challenges** by establishing a structured Spring Boot application with BCrypt password hashing, Spring Security 6 role authorization (`ROLE_USER` and `ROLE_ADMIN`), an automated moderation pipeline, and a dedicated Jakarta `HttpServlet` using direct JDBC queries for rapid JSON exports.

---

## 3. Implemented Features by Role.

### 🌐 Public / Guest User Features
* **Public Recipe Catalog:** Browse all approved recipes (`/recipes`) with category filter pills (e.g., *Chicken*, *Paneer*, *Vegetarian*, *South Indian*, *Snacks*, *Chinese*).
* **Recipe Search:** Search recipes by title or ingredient keywords (`/recipes/search`).
* **Recipe Details View:** View complete ingredients, step-by-step instructions, preparation images, average star ratings, and public reviews (`/recipes/details/{id}`).
* **Direct JSON Export Endpoint:** Access public approved recipe data in JSON format via native Jakarta Servlet (`/api/recipes/export`).
* **User Account Registration & Login:** Create a new account with validation or authenticate using email and password (`/register`, `/login`).

### 👤 Registered User Features (`ROLE_USER`).
* **Personal User Dashboard:** View account summary, submitted recipe count, saved collections, and latest submitted recipe statuses (`/dashboard`).
* **Recipe Submission:** Submit recipes with title, category description tag, ingredients, step-by-step instructions, and image upload attachments (`/recipes/create`). Newly created recipes receive `PENDING` status.
* **Recipe Status Tracking:** Monitor real-time moderation status (`PENDING`, `APPROVED`, `REJECTED`) from the personal workspace (`/recipes/my-recipes`).
* **Recipe Editing & Deletion:** Modify or remove personal recipe submissions (`/recipes/edit/{id}`, `/recipes/delete/{id}`).
* **Personal Collections (Bookmarks):** Save favorite public recipes to personal collections with one-click bookmarking and removal (`/collections/add/{id}`, `/collections/remove/{id}`, `/collections`).
* **Interactive Ratings & Reviews:** Rate recipes on a 1-5 star scale and publish textual reviews (`/ratings/submit`, `/reviews/add`).
* **Profile Management:** Update personal account details and change passwords (`/profile`, `/profile/update`, `/profile/change-password`).

### 🛡️ Administrator Features (`ROLE_ADMIN`).
* **Administrator Console:** Centralized dashboard displaying live platform statistics: Total Registered Users, Pending Recipe Approvals, Approved Recipes, and Total Reviews (`/admin/dashboard`).
* **Recipe Moderation Queue:** Review pending recipe submissions with full details, then perform one-click approval (`/admin/recipes/approve/{id}`) or rejection (`/admin/recipes/reject/{id}`).
* **User Management Console:** Inspect all registered accounts, promote users to `ROLE_ADMIN`, demote admins to `ROLE_USER`, or delete user accounts (`/admin/users`, `/admin/users/promote/{id}`, `/admin/users/demote/{id}`, `/admin/users/delete/{id}`).
* **Global Content Moderation:** Administrative override to delete any recipe across the system (`/admin/recipes/delete/{id}`).

---

## 4. Technology Stack & Prerequisites.

| Layer | Technology / Tool | Version / Details |
| :--- | :--- | :--- |
| **Language** | Java | 17 LTS |
| **Backend Framework** | Spring Boot | 3.2.3 (`spring-boot-starter-web`, `spring-boot-starter-validation`) |
| **Security Framework** | Spring Security | 6.0 (`spring-boot-starter-security`, BCrypt Hashing) |
| **ORM / Persistence** | Spring Data JPA / Hibernate | Hibernate 6.x (`spring-boot-starter-data-jpa`) |
| **Direct Database Access** | Java Database Connectivity (JDBC) | `java.sql` (`Connection`, `PreparedStatement`, `ResultSet`) |
| **Servlet Container API** | Jakarta Servlet API | Jakarta HttpServlet via `@WebServlet` and `@ServletComponentScan` |
| **Database** | MySQL Server | 8.x (`com.mysql:mysql-connector-j`) |
| **Template Engine** | Thymeleaf | 3.x with Spring Security 6 dialect integration (`thymeleaf-extras-springsecurity6`) |
| **Frontend Framework & Styling** | Custom CSS & Bootstrap | Bootstrap 5.3.2, FontAwesome 6.4.2, Google Fonts ('Outfit') |
| **Boilerplate Reduction** | Lombok | Project Lombok (`org.projectlombok:lombok`) |
| **Build Tool** | Apache Maven | 3.8+ |

### Prerequisites.
* **JDK 17** or higher installed and configured in `JAVA_HOME`.
* **MySQL 8.x** running locally on port `3306`.
* **Apache Maven 3.8+** (or use system `mvn` command).

---

## 5. System Architecture & Layered Design.

DishVerse follows a clean multi-tier architecture adhering to standard Enterprise Java patterns. Requests flow through predefined layers to ensure strict separation of concerns, maintainability, and testability.

### 🏢 Architectural Layers.
1. **Client / View Layer (Thymeleaf & Static Assets):** Renders dynamic HTML templates embedded with Thymeleaf directives (`th:text`, `th:each`, `sec:authorize`). Styled with Bootstrap 5.3.2 and custom CSS variables defined in `/css/style.css`.
2. **Security & Filter Chain Layer (Spring Security 6):** Intercepts incoming HTTP requests, performs BCrypt user authentication, evaluates role permissions (`ROLE_USER` / `ROLE_ADMIN`), and handles session invalidation.
3. **Servlet & Controller Layer (Spring MVC & Jakarta Servlet):**
   * **Spring MVC Controllers:** Handle web requests, perform form validation (`@Valid`), extract security details from `SecurityUtils`, and delegate processing to service components.
   * **Jakarta HttpServlet:** `RecipeExportServlet` maps directly to `/api/recipes/export` for direct JSON serialization without going through the MVC stack.
4. **Service Layer (Business Logic):** Encapsulates core workflows, transactional boundaries (`@Transactional`), DTO conversions, status state transitions (`PENDING` → `APPROVED`), and file upload persistence (`FileUploadUtil`).
5. **Repository & JDBC Helper Layer:**
   * **Spring Data JPA Repositories:** Standard data access interfaces extending `JpaRepository` for object-relational mapping.
   * **JdbcDatabaseHelper:** Component executing raw SQL queries via `java.sql` driver interfaces.
6. **Database Layer (MySQL 8.x):** Relational store containing persistent tables (`users`, `recipes`, `ratings`, `reviews`, `recipe_collections`, `system_settings`).

```
                              ┌──────────────────────────────────────────────────┐
                              │           Client Web Browser                     │
                              └──────────┬────────────────────────────┬──────────┘
                                         │                            │
                            HTTP Request │ (Standard Web Navigation)  │ HTTP GET /api/recipes/export
                                         ▼                            ▼
                 ┌──────────────────────────────────────┐  ┌──────────────────────────────────┐
                 │    Spring Security Filter Chain     │  │    Spring Security Filter Chain  │
                 │   (BCrypt Auth, Role Evaluation)     │  │    (PermitAll Authorization)     │
                 └──────────────────┬───────────────────┘  └────────────────┬─────────────────┘
                                    │                                       │
                                    ▼                                       ▼
                 ┌──────────────────────────────────────┐  ┌──────────────────────────────────┐
                 │          Controller Layer            │  │     RecipeExportServlet          │
                 │      (Spring MVC Controllers)        │  │     (@WebServlet - Jakarta)      │
                 └──────────────────┬───────────────────┘  └────────────────┬─────────────────┘
                                    │                                       │
                                    ▼                                       │
                 ┌──────────────────────────────────────┐                   │ Direct JDBC
                 │           Service Layer              │                   │ Execution
                 │     (Business Logic & Workflows)     │                   │
                 └──────────────────┬───────────────────┘                   │
                                    │                                       │
                                    ▼                                       ▼
                 ┌──────────────────────────────────────┐  ┌──────────────────────────────────┐
                 │          Repository Layer            │  │        JdbcDatabaseHelper        │
                 │      (Spring Data JPA Repos)         │  │     (java.sql PreparedStatement) │
                 └──────────────────┬───────────────────┘  └────────────────┬─────────────────┘
                                    │                                       │
                                    └───────────────────┬───────────────────┘
                                                        │ SQL Queries
                                                        ▼
                                   ┌──────────────────────────────────────────┐
                                   │      MySQL Database (`recipe_sharing_db`)│
                                   └──────────────────────────────────────────┘
```

---

## 6. Actual Project Directory Structure

```
d:/Java Project/
├── .gitignore
├── LICENSE
├── pom.xml                                    # Maven Project Configuration & Dependencies
├── README.md                                  # Academic Rubric Project Documentation
├── uploads/                                   # File Upload Directory
│   └── recipes/                               # Saved Recipe Images (Seeded & User Uploads)
│       ├── aloo-gobi.jpg
│       ├── butter-chicken.jpg
│       ├── chicken-biryani.jpg
│       ├── chicken-curry.jpg
│       ├── chicken-tikka-masala.jpg
│       ├── chole-masala.jpg
│       ├── garlic-noodles.jpg
│       ├── kadai-paneer.jpg
│       ├── masala-dosa.jpg
│       ├── palak-paneer.jpg
│       ├── paneer-butter-masala.jpg
│       ├── paneer-tikka.jpg
│       ├── rajma-masala.jpg
│       ├── veg-biryani.jpg
│       └── veg-sandwich.jpg
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── recipeshare/
        │           ├── RecipeSharingApplication.java      # Main Entry Point with @ServletComponentScan
        │           ├── config/                            # Spring Configuration Classes
        │           │   ├── DataInitializer.java          # DB Seed Component (Admin & Seed Recipes)
        │           │   ├── SecurityConfig.java           # Spring Security 6 Filter Chain & PasswordEncoder
        │           │   └── WebMvcConfig.java             # Static Resource Handler for Uploads
        │           ├── controller/                        # Spring MVC Web Controllers
        │           │   ├── AdminController.java          # Admin Moderation & User Management
        │           │   ├── AuthController.java           # User Registration & Login Endpoints
        │           │   ├── CollectionController.java     # Recipe Bookmarking Operations
        │           │   ├── HomeController.java           # Root Landing & Search Handling
        │           │   ├── ProfileController.java        # User Profile Updates & Password Reset
        │           │   ├── RecipeController.java         # Recipe CRUD & Status Tracking
        │           │   └── ReviewController.java         # Ratings & Review Submissions
        │           ├── dto/                               # Data Transfer Objects with Bean Validation
        │           │   ├── RecipeDto.java
        │           │   ├── ReviewDto.java
        │           │   ├── UserProfileDto.java
        │           │   └── UserRegistrationDto.java
        │           ├── entity/                            # JPA Database Entities
        │           │   ├── Rating.java                   # User Star Ratings (1-5)
        │           │   ├── Recipe.java                   # Core Recipe Data & Moderation Status
        │           │   ├── RecipeCollection.java         # User Bookmarks Mapping
        │           │   ├── Review.java                   # User Textual Reviews
        │           │   ├── SystemSetting.java            # Application Key-Value Configuration
        │           │   └── User.java                     # User Accounts & Role Enums
        │           ├── enums/                             # Domain Enumerations
        │           │   ├── RecipeStatus.java             # PENDING, APPROVED, REJECTED
        │           │   └── Role.java                     # ROLE_USER, ROLE_ADMIN
        │           ├── exception/                         # Custom Application Exceptions
        │           │   ├── GlobalExceptionHandler.java
        │           │   ├── ResourceNotFoundException.java
        │           │   └── UnauthorizedAccessException.java
        │           ├── repository/                        # Spring Data JPA Interfaces
        │           │   ├── RatingRepository.java
        │           │   ├── RecipeCollectionRepository.java
        │           │   ├── RecipeRepository.java
        │           │   ├── ReviewRepository.java
        │           │   ├── SystemSettingRepository.java
        │           │   └── UserRepository.java
        │           ├── security/                          # Spring Security Details Integration
        │           │   ├── CustomUserDetails.java        # Implements org.springframework.security.core.userdetails.UserDetails
        │           │   └── CustomUserDetailsService.java # Implements UserDetailsService interface
        │           ├── service/                           # Business Logic Interfaces & Implementations
        │           │   ├── AdminService.java
        │           │   ├── AdminServiceImpl.java
        │           │   ├── CollectionService.java
        │           │   ├── CollectionServiceImpl.java
        │           │   ├── RecipeService.java
        │           │   ├── RecipeServiceImpl.java
        │           │   ├── ReviewService.java
        │           │   ├── ReviewServiceImpl.java
        │           │   ├── UserService.java
        │           │   └── UserServiceImpl.java
        │           ├── servlet/                           # Native Jakarta Servlets
        │           │   └── RecipeExportServlet.java      # Serves /api/recipes/export JSON via Direct JDBC
        │           └── util/                              # Helper & Utility Components
        │               ├── FileUploadUtil.java           # Multipart File Storage Handler
        │               ├── JdbcDatabaseHelper.java       # java.sql Direct JDBC Query Component
        │               └── SecurityUtils.java            # Context Helper for Current Principal
        └── resources/
            ├── application.properties                 # Environment Configurable Application Properties
            ├── static/
            │   └── css/
            │       └── style.css                      # DishVerse CSS Tokens & Components
            └── templates/                             # Thymeleaf Views
                ├── index.html                         # Home Landing Page
                ├── layout.html                        # Base Layout Skeleton
                ├── admin/
                │   ├── dashboard.html                 # Platform Metrics Dashboard
                │   ├── recipes.html                   # Recipe Moderation Queue
                │   └── users.html                     # User Account Management
                ├── auth/
                │   ├── login.html                     # User Sign-In Form
                │   └── register.html                  # User Sign-Up Form
                ├── collection/
                │   └── list.html                      # Personal Saved Recipes List
                ├── error/
                │   ├── 403.html                       # Access Denied Error Page
                │   ├── 404.html                       # Resource Not Found Page
                │   └── 500.html                       # Internal Server Error Page
                ├── fragments/
                │   └── common.html                    # Reusable Navbar & Footer Fragments
                ├── recipe/
                │   ├── create.html                    # Submit New Recipe Form
                │   ├── details.html                   # Public Recipe Details & Reviews
                │   ├── edit.html                      # Edit Recipe Form
                │   ├── list.html                      # Public Catalog Browse Page
                │   └── my-recipes.html                # User Workspace Recipe Status Tracker
                └── user/
                    ├── dashboard.html                 # User Workspace Dashboard
                    └── profile.html                   # Profile Settings & Password Change
```

---

## 7. Database Setup & Entity Relationships

Database Name: **`recipe_sharing_db`**

DishVerse automatically initializes table definitions on startup using Hibernate DDL Auto (`spring.jpa.hibernate.ddl-auto=update`).

### 🗄️ Relational Schema Details

1. **`users` Table (`User.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `name` (VARCHAR(100), Not Null)
   * `email` (VARCHAR(120), Unique, Not Null)
   * `password` (VARCHAR(255), BCrypt Hashed String, Not Null)
   * `role` (VARCHAR(20), Enum: `ROLE_USER` / `ROLE_ADMIN`, Not Null)
   * `created_at` (DATETIME, Auto-Populated)
   * `updated_at` (DATETIME)

2. **`recipes` Table (`Recipe.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `user_id` (BIGINT, Foreign Key referencing `users.id`, Not Null)
   * `title` (VARCHAR(150), Not Null)
   * `description` (TEXT, Not Null)
   * `ingredients` (TEXT, Not Null)
   * `instructions` (TEXT, Not Null)
   * `image_url` (VARCHAR(255))
   * `status` (VARCHAR(20), Enum: `PENDING` / `APPROVED` / `REJECTED`, Not Null)
   * `created_at` (DATETIME, Auto-Populated)
   * `updated_at` (DATETIME)

3. **`ratings` Table (`Rating.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `recipe_id` (BIGINT, Foreign Key referencing `recipes.id`, Not Null)
   * `user_id` (BIGINT, Foreign Key referencing `users.id`, Not Null)
   * `rating` (INT, 1 to 5, Not Null)
   * `created_at` (DATETIME, Auto-Populated)
   * *Constraint:* Unique Composite Index `uk_rating_user_recipe (recipe_id, user_id)` preventing duplicate user ratings on a single recipe.

4. **`reviews` Table (`Review.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `recipe_id` (BIGINT, Foreign Key referencing `recipes.id`, Not Null)
   * `user_id` (BIGINT, Foreign Key referencing `users.id`, Not Null)
   * `comment` (TEXT, Not Null)
   * `created_at` (DATETIME, Auto-Populated)
   * `updated_at` (DATETIME)

5. **`recipe_collections` Table (`RecipeCollection.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `user_id` (BIGINT, Foreign Key referencing `users.id`, Not Null)
   * `recipe_id` (BIGINT, Foreign Key referencing `recipes.id`, Not Null)
   * `created_at` (DATETIME, Auto-Populated)
   * *Constraint:* Unique Composite Index `uk_collection_user_recipe (user_id, recipe_id)` preventing duplicate bookmarking.

6. **`system_settings` Table (`SystemSetting.java`)**
   * `id` (BIGINT, Primary Key, Auto-Increment)
   * `setting_key` (VARCHAR(100), Unique, Not Null)
   * `setting_value` (VARCHAR(255))

---

## 8. Environment Variables Configuration

DishVerse uses environment variable injection with sensible local fallbacks defined in `src/main/resources/application.properties`.

### Required & Optional Environment Variables

| Variable | Description | Required | Default Fallback / Handling |
| :--- | :--- | :---: | :--- |
| `DB_URL` | MySQL Connection JDBC URL | Optional | `jdbc:mysql://localhost:3306/recipe_sharing_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USERNAME` | MySQL Database Username | Optional | `root` |
| `DB_PASSWORD` | MySQL Database Password | **Required** | *(Empty string / Must be provided)* |
| `PORT` | Application HTTP Server Port | Optional | `8080` |
| `APP_ADMIN_EMAIL` | Administrator Account Email | Optional | *(Configured via environment variable / fallback in code)* |
| `APP_ADMIN_PASSWORD` | Administrator Account Password | Optional | *(Configured via environment variable / fallback in code)* |
| `UPLOAD_DIR` | Directory for uploaded recipe photos | Optional | `uploads/recipes/` |

### How Admin Account Auto-Initialization Works
On startup, `DataInitializer.java` reads `@Value("${app.admin.email}")` and `@Value("${app.admin.password}")` (which bind to `APP_ADMIN_EMAIL` and `APP_ADMIN_PASSWORD` environment variables). If no user with that email exists, `DataInitializer` automatically creates an administrator account assigned with `ROLE_ADMIN` using source-code fallback defaults unless custom values are explicitly configured.

Users should explicitly set their own `APP_ADMIN_EMAIL` and `APP_ADMIN_PASSWORD` environment variables before starting the application.

---

## 9. Windows PowerShell Setup & Execution Guide

Follow these exact steps in **Windows PowerShell** to configure environment variables, compile, and run DishVerse:

### Step 1: Clone Repository and Navigate to Project Root
```powershell
git clone https://github.com/Veeru2807/Dishverse-online-recipe-sharing-platform.git
cd "Dishverse-online-recipe-sharing-platform"
```

### Step 2: Ensure Local MySQL Service is Running
Ensure MySQL Server 8.x is active on port `3306`. The database `recipe_sharing_db` will automatically be created on launch if it does not exist due to `createDatabaseIfNotExist=true`.

### Step 3: Configure Environment Variables in PowerShell
Replace `<your_mysql_password>`, `<your_admin_email>`, and `<your_strong_admin_password>` with your actual values:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/recipe_sharing_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="<your_mysql_password>"
$env:PORT="8080"
$env:APP_ADMIN_EMAIL="<your_admin_email>"
$env:APP_ADMIN_PASSWORD="<your_strong_admin_password>"
```

> [!NOTE]
> Environment variables set via `$env:` are scoped to the active PowerShell terminal session.

### Step 4: Clean and Build the Application
Compile source code and verify build integrity:
```powershell
mvn clean compile
```

### Step 5: Start the Spring Boot Application
```powershell
mvn spring-boot:run
```

Once initialized, terminal output will display Spring Boot startup completion on port `8080`.

---

## 10. Local Application Access Guide

After starting the application, open your web browser:

* **Public Web Interface:** Navigating to `http://localhost:8080/` opens the Home page.
* **Public Recipe Catalog:** Browse all approved recipes at `http://localhost:8080/recipes`.
* **Direct JDBC Export Servlet:** Access raw JSON output of approved public recipes at `http://localhost:8080/api/recipes/export`.

### Account Testing Workflows

1. **User Sign-Up & Workflow (`ROLE_USER`):**
   - Click **Register** (`/register`) to create a new user account.
   - Log in using your registered credentials.
   - Access **User Dashboard** (`/dashboard`), click **Submit Recipe** (`/recipes/create`), fill in recipe details, attach an image, and submit.
   - Note that the newly created recipe status is set to `PENDING` in **My Recipes** (`/recipes/my-recipes`).

2. **Admin Workflow (`ROLE_ADMIN`):**
   - Log in using your configured administrator credentials (`<your_admin_email>` / `<your_strong_admin_password>`).
   - Access **Admin Dashboard** (`/admin/dashboard`) to view live metrics.
   - Open **Recipe Moderation Queue** (`/admin/recipes`) to approve or reject pending user submissions.
   - Once approved, return to the public catalog (`/recipes`) to verify that the approved recipe is visible.
   - Manage platform users via **User Management** (`/admin/users`).

---

## 11. Authentication, Role-Based Authorization & Password Hashing

Security features are configured in `com.recipeshare.config.SecurityConfig` using Spring Security 6.

### 🔐 Password Hashing
User passwords are encrypted before storage using **BCrypt** (`BCryptPasswordEncoder`). Raw passwords are never stored in plaintext. During registration, `UserServiceImpl` calls `passwordEncoder.encode(rawPassword)`.

### 🛡️ Authentication Architecture
* **`CustomUserDetailsService`:** Implements Spring Security's `UserDetailsService`, fetching user records from `UserRepository` by email.
* **`CustomUserDetails`:** Wraps `User` entity, implementing `UserDetails` and exposing user authorities derived from `Role` (`ROLE_USER` or `ROLE_ADMIN`).
* **`DaoAuthenticationProvider`:** Wired with `CustomUserDetailsService` and `BCryptPasswordEncoder` to authenticate submitted login requests (`/login`).

### 🚦 Role Authorization Matrix (`SecurityConfig.java`)

```java
.authorizeHttpRequests(auth -> auth
    // Public Routes (Permit All)
    .requestMatchers(
        "/", "/recipes", "/recipes/details/**", "/recipes/search",
        "/register", "/login", "/css/**", "/js/**", "/images/**",
        "/uploads/**", "/api/recipes/export"
    ).permitAll()

    // Admin Only Routes
    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

    // Authenticated User & Admin Shared Routes
    .requestMatchers(
        "/dashboard", "/recipes/create", "/recipes/my-recipes",
        "/recipes/edit/**", "/recipes/delete/**", "/collections/**",
        "/reviews/**", "/ratings/**", "/profile/**"
    ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

    .anyRequest().authenticated()
)
```

---

## 12. JDBC Integration & Recipe Export Servlet

To satisfy dual database access requirements, DishVerse combines Spring Data JPA ORM with native **Java Database Connectivity (JDBC)** and **Jakarta Servlets**.

### ⚡ Implementation Blueprint

1. **`JdbcDatabaseHelper.java` (`com.recipeshare.util`):**
   A Spring `@Component` that uses standard `java.sql` classes (`DriverManager`, `Connection`, `PreparedStatement`, `ResultSet`) to query MySQL directly, bypassing Hibernate context overhead.

```java
public List<Map<String, Object>> fetchApprovedRecipesForExport() {
    List<Map<String, Object>> recipes = new ArrayList<>();
    String sql = "SELECT id, title, description, ingredients, instructions, image_url, created_at FROM recipes WHERE status = ? ORDER BY id ASC";

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "APPROVED");

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("id", rs.getLong("id"));
                recipe.put("title", rs.getString("title"));
                recipe.put("description", rs.getString("description"));
                recipe.put("ingredients", rs.getString("ingredients"));
                recipe.put("instructions", rs.getString("instructions"));
                recipe.put("imageUrl", rs.getString("image_url"));
                recipe.put("createdAt", rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toString() : null);
                recipes.add(recipe);
            }
        }
    } catch (SQLException e) {
        System.err.println("JDBC Execution Error: " + e.getMessage());
    }
    return recipes;
}
```

2. **`RecipeExportServlet.java` (`com.recipeshare.servlet`):**
   Extends `jakarta.servlet.http.HttpServlet` and is annotated with `@WebServlet(name = "RecipeExportServlet", urlPatterns = "/api/recipes/export")`. It handles `GET` requests (`doGet`), sets `application/json` content type with `UTF-8` encoding, and uses Jackson `ObjectMapper` to convert raw JDBC result maps into pretty-printed JSON output directly on the `HttpServletResponse` output stream.

3. **`RecipeSharingApplication.java`:**
   Annotated with `@ServletComponentScan` so Spring Boot auto-registers native Jakarta servlets at startup.

---

## 13. Build & Testing Commands

Execute these Maven commands in your project root:

* **Compile Source Code:**
  ```bash
  mvn clean compile
  ```
* **Run Unit & Integration Tests:**
  ```bash
  mvn test
  ```
* **Package Executable JAR File:**
  ```bash
  mvn clean package -DskipTests
  ```
* **Execute Packaged JAR File:**
  ```bash
  java -jar target/online-recipe-sharing-platform-1.0.0-SNAPSHOT.jar
  ```

---

## 14. Troubleshooting Guide

### 1. `Access denied for user 'root'@'localhost'` (MySQL Error)
* **Cause:** `DB_PASSWORD` environment variable is either incorrect or missing.
* **Solution:** Verify your local MySQL password and execute `$env:DB_PASSWORD="<correct_password>"` in your active PowerShell session before launching.

### 2. `Port 8080 is already in use`
* **Cause:** Another local process or web application is occupying port 8080.
* **Solution:** Set a different port before launching:
  ```powershell
  $env:PORT="8081"
  mvn spring-boot:run
  ```
  Then access the application at `http://localhost:8081`.

### 3. `Unknown database 'recipe_sharing_db'`
* **Cause:** MySQL server requires auto-creation permissions.
* **Solution:** Ensure `DB_URL` includes `createDatabaseIfNotExist=true` parameter or manually create the database in MySQL Workbench / CLI: `CREATE DATABASE recipe_sharing_db;`.

### 4. `java: invalid target release: 17`
* **Cause:** JDK 17 is not configured as your default Java runtime environment.
* **Solution:** Verify your Java version using `java -version` and point `JAVA_HOME` environment variable to a valid JDK 17 installation.

---

## 15. GitHub Repository Link

* **GitHub Repository:** [https://github.com/Veeru2807/Dishverse-online-recipe-sharing-platform](https://github.com/Veeru2807/Dishverse-online-recipe-sharing-platform)
* **Git Clone Command:** `git clone https://github.com/Veeru2807/Dishverse-online-recipe-sharing-platform.git`

---

## 16. Application Screenshots

| Home Page Hero & Catalog | Recipe Detail View |
| :---: | :---: |
| ![Home Page](https://via.placeholder.com/600x350?text=DishVerse+Home+Catalog+Screenshot) | ![Recipe Details](https://via.placeholder.com/600x350?text=Recipe+Details+%26+Reviews+Screenshot) |

| User Workspace Dashboard | Admin Moderation Console |
| :---: | :---: |
| ![User Dashboard](https://via.placeholder.com/600x350?text=User+Workspace+Dashboard+Screenshot) | ![Admin Console](https://via.placeholder.com/600x350?text=Admin+Moderation+Queue+Screenshot) |

---

## 17. Limitations & Future Scope

### Current Limitations
* **Local Disk Storage for Recipe Uploads:** Uploaded recipe images are stored on the local application server filesystem under `uploads/recipes/` rather than a dedicated cloud storage service.
* **Single-Instance Deployment:** Session state relies on container memory, requiring sticky sessions for horizontal scaling across multiple application instances.
* **Manual Content Moderation:** Recipe approvals rely entirely on manual administrative inspection without automated AI content filtering.

### Future Scope
* **Cloud Object Storage:** Integrate Amazon S3 or Cloudinary for cloud photo attachments and CDN delivery.
* **Nutrition API Integration:** Connect third-party nutrition APIs (e.g., Spoonacular / Edamam) to automatically compute calories and macronutrients per recipe.
* **Social Sharing & Print Integration:** One-click recipe sharing to social platforms and print-formatted recipe PDF generation.
* **Video Cooking Tutorials:** Add YouTube/Vimeo video embed capabilities to recipe creation forms.

---

## 18. Author Information

* **Veer Singh Rathor** — Project Lead & Full-Stack Developer ([Veeru2807](https://github.com/Veeru2807))
* **Shreya Agrawal** — Testing & Quality Assurance
* **Manvendra Singh** — Documentation & UI Review

**Project Name:** DishVerse – Online Recipe Sharing Platform
**Purpose:** Academic Portfolio Submission, Technical Viva Demonstration, and GitHub Open-Source Showcase.
