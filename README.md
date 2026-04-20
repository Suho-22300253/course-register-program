<Member>

Student Number: 22300253
Name: 명수호
<Title>
Title: Course Register Program

<Project description>
Program performance
1. Create Account
2. Login
3. The professor could create the course
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

How does it work
1. Strat program


2. Create Account
![Create Account - Student.png](CodeExecutionFolder/Create%20Account%20-%20Student.png)


3. Login - Student
![Login - Student.png](CodeExecutionFolder/Login%20-%20Student.png)


4. Student - Register Course
![StudentMenu - register course and show my course.png](CodeExecutionFolder/StudentMenu%20-%20register%20course%20and%20show%20my%20course.png)
![StudentMenu - register course and show my course2.png](CodeExecutionFolder/StudentMenu%20-%20register%20course%20and%20show%20my%20course2.png)


5. Student - Drop Course
![StudentMenu - Drop course.png](CodeExecutionFolder/StudentMenu%20-%20Drop%20course.png)


6. Exit and register course.txt
![Register course text file.png](CodeExecutionFolder/Register%20course%20text%20file.png)


7. Login and Add course - Professor
![Professor - Login and Add course.png](CodeExecutionFolder/Professor%20-%20Login%20and%20Add%20course.png)


8. Professor - show my course
![Professor - show my course.png](CodeExecutionFolder/Professor%20-%20show%20my%20course.png)


9. After Professor add new course and change of StudentMenu - register course
![Student - after professor add new course.png](CodeExecutionFolder/Student%20-%20after%20professor%20add%20new%20course.png)


UML DIAGRAM
user pacakge
![users package UML.png](CodeExecutionFolder/users%20package%20UML.png)

program pacakge
![program package UML.png](CodeExecutionFolder/program%20package%20UML.png)

manager pacakge
![manager package UML.png](CodeExecutionFolder/manager%20package%20UML.png)

![manager package UML2.png](CodeExecutionFolder/manager%20package%20UML2.png)

course package
![course package UML.png](CodeExecutionFolder/course%20package%20UML.png)

youtube link
https://youtu.be/VaU65yn3ndc