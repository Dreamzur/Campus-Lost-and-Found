# Campus Lost and Found

Losing an item on campus can feel like it's gone for good especially with the outdated and inefficient systems currently in place. Our full-stack web and mobile application makes it easy for students and faculty to report, search for, and manage lost and found items. With features like location tagging, image uploads, and role-based admin tools, the platform simplifies the recovery process and improves transparency. Built using React, Spring Boot, and MySQL, it’s designed to help users reconnect with their belongings quickly and efficiently.

---

## Project Structure
- `/frontend` — React + Vite frontend
- `/backend` — Spring Boot backend
- `/AndroidApp/FIULostAndFoundAndroid` — Mobile application (Android)

##  In Development

###  Upcoming Features
- Real-time notification system for item status updates  
- In-app chat between users and staff  
- Image matching (automated suggestions based on uploaded photos)

---
## Features

- **User Registration & Login**  
  Secure authentication for users via web and mobile platforms.

- **Token-Based Authentication**  
  JWT tokens ensure secure and stateless backend session handling.

- **Item Reporting**  
  Report lost or found items with a title, description, image, and location.

- **Item Listing**  
  Browse all submitted items in a user-friendly, organized UI.

- **Item Claiming**  
  Admins can mark items as claimed to manage inventory and prevent duplicates.

- **Search & Filtering**
  Easily locate specific items using filters and keyword search.

- **Mobile Support**  
  Fully responsive design plus a native Android app for mobile users.

- **Admin Dashboard**  
  Admins can view, approve, delete, or manage submitted reports.

- **Role-Based Access Control**  
  Restricts admin functionality and sensitive actions to authorized users.

- **Image Uploads** *(needs tweaking)*    
  Upload and display item photos. Feature is functional but undergoing refinement.

---
###  Known Limitations
- **No Real-Time Follow-Up Notifications**  
  Users aren't yet notified when updates are made to their item listings (e.g., approval or claim status).

- **Duplicate Detection Not Implemented**  
  The system currently does not flag or merge duplicate item listings, which may cause clutter or confusion.

- **Missing Report Feature**  
  Users cannot report suspicious or false listings yet, limiting moderation and platform quality control.

---
##  How to Run Locally

### 1. Clone the repository
```bash
git clone https://github.com/Dreamzur/Campus-Lost-and-Found
cd Campus-Lost-and-Found
```
>  Make sure you have Node.js, Java 17+, and MySQL installed

### 2. Navigate to the frontend and install dependencies
```bash
cd frontend
npm install
```

### 3. Start the React app
```bash
npm run dev
```

### 4. Start the backend Spring Boot server
Open a new terminal and run:
```bash
cd backend
./gradlew bootRun
```

>  Make sure you have Java 17+ and MySQL running locally (with credentials configured in `application.properties`).

#### Set up the database:
Create a MySQL database and update the following properties in `application.properties`:
- spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
- spring.datasource.username=your_username
- spring.datasource.password=your_password

---

##  Dependencies
> The following dependencies power the frontend and backend components of the project.
###  Frontend (`React + Vite`)
- `react` ^19.1.0  
- `react-dom` ^19.1.0  
- `react-router-dom` ^7.6.1  
- `axios` ^1.10.0  
- `vite` ^6.3.5  
- Dev Tools:
  - `eslint`
  - `eslint-plugin-react-hooks`
  - `@vitejs/plugin-react`

###  Backend (`Spring Boot`)
- Spring Boot Starters:
  - `spring-boot-starter-web`
  - `spring-boot-starter-security`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-mail`
- `MySQL Connector` 8.0.33  
- `Jakarta Persistence API` 3.1.0  
- `JJWT` (JWT Authentication) 0.11.5  
- `Gson` 2.10.1  
- `JUnit` for testing  

---

##  Collaborators

- **Christian Stewart**  
- **Jonathan Hori**  
- **Cristian Pena**  
- **Randy Arbolaez**  
- **Dahiver Guerra**
