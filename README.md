Student Number: 22300253

Name: 명수호

Title: Course Register Program

Program performance
1. Create Account
2. Login
3. The professor could create or modify the course
4. The student could register for the course

Project Introduction

This is a course registration program in which users can create an account and log in to access functions based on their role. Students can register for courses, drop courses, view their registered courses, and check their total credits. Professors can create courses and view the courses they have opened.

Main Features
1. Account Creation - Students and professors can create their own accounts. Account information is stored in accounts.txt, so users can log in with the same account even after the program is closed.

2. Login / Logout - Users can log in with their created account and log out after using the program.

3. Student Functions - Students can use the following features:
- Register for courses
- Drop courses
- View registered courses
- Check total registered credits

4. Professor Functions - Professors can use the following features:
- Create courses
- View opened courses 

Project Structure
main
This is the starting point of the program. It provides the basic menu shown when the program begins, such as account creation, login, and system exit.

program
This package handles the detailed flow of the menu system. It manages account creation, login, and the different menus shown after login depending on whether the user is a student or a professor.

manager
This package handles the core functions related to students, professors, and courses. It includes overall logic such as account management, course management, course registration, and course withdrawal.

users
This package defines the common information shared by users and the specific characteristics of student and professor classes.

course
This package contains the course class and manages course-related information such as course name, credit, and professor name.
