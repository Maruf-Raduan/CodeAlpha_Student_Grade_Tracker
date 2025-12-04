# Student Grade Tracker

A professional Java Swing application for tracking and managing student grades. This project was developed as part of the CodeAlfa Java Internship.

## Features

- **Professional GUI**: Built with Java Swing, featuring a modern tabbed interface.
- **Dashboard**: Real-time overview of class performance, including total students, class average, and top performer.
- **Student Management**:
    - **Registration**: Add students with unique Registration Numbers.
    - **Course Details**: Record grades with specific Course Codes and Subject Names.
    - **Detailed View**: View complete grade history and performance status for each student.
- **Performance Indicators**: Visual color-coding (Green/Red) to instantly highlight student performance status (Excellent, Good, Average, Needs Improvement).

## Project Structure

```
src/
├── GradeTrackerGUI.java  # Main application entry point and GUI logic
├── Student.java          # Student entity class
└── Grade.java            # Grade entity class (Score, Course, Subject)
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher.

### Installation & Running

1.  **Clone the repository**:
    ```bash
    git clone  https://github.com/Maruf-Raduan/CodeAlpha_Student_Grade_Tracker.git
    cd CodeAlpha_Student_Grade_Tracker
    ```

2.  **Compile the source code**:
    ```bash
    javac -d bin src/*.java
    ```

3.  **Run the application**:
    ```bash
    java -cp bin GradeTrackerGUI
    ```

## Usage

1.  **Launch the App**: Run the command above to start the GUI.
2.  **Add a Student**:
    - Go to the "Student Management" tab.
    - Enter the Registration Number, Name, Course Code, Subject, and Grade.
    - Click "Add Record".
3.  **View Details**:
    - Select a student from the table on the left.
    - The right panel will show their full details, including average score and performance status.
4.  **Check Dashboard**:
    - Switch to the "Dashboard" tab to see the overall class statistics.

## License

This project is open source and available under the [MIT License](LICENSE).
