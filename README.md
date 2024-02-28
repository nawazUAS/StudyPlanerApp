### Cloning and Setting Up StudyPlanerApp

Thank you for choosing the StudyPlanerApp! Follow these simple steps to get started with the Angular frontend after cloning or forking the repository.

---

### Prerequisites:

- Make sure you have Node.js and npm installed on your machine:
To download and install Angular, visit the official Angular website: [Angular - Get Started](https://angular.io/guide/setup).
Follow the instructions provided to ensure you have the latest version of Angular CLI installed on your machine before running the StudyPlanerApp.

- The backend is designed using Spring Boot, so ensure you have Java and Spring Boot set up.

---

### Step 1: Clone or Fork

Clone or fork the StudyPlanerApp repository to your local machine using the following command:

```bash
git clone https://github.com/nawazUAS/StudyPlanerApp.git
```

---

### Step 2: Navigate to Angular App

Move into the AngularApp directory using the terminal:

```bash
cd AngularApp/StudyPlanerApp
```

---

### Step 3: Install Dependencies

Run the following command to install the necessary dependencies for the Angular App:

```bash
npm install
```

This will download and install all the required packages specified in the `package.json` file.

---

### Step 4: Set Up Backend (Spring Boot)

Make sure to set up and run the backend, which is designed using Spring Boot.

---

### Step 5: Run Angular App

Once the dependencies are installed and the backend is up and running, start the Angular App:

```bash
ng serve -o
```
or to use it on your mobile device:
```bash
ng serve --host 0.0.0.0
```

This will launch the app, and you can access it in your browser at `http://localhost:4200/`.

---

### Additional Notes:

- Ensure that the backend e.g. the gateway-service is running on the specified port (`http://localhost:8085/`), as the frontend communicates with it.

- Make sure you have the necessary configurations for the backend, especially if you have made any changes.

---

Now you're all set to use the StudyPlanerApp!

Happy studying! 📚🚀
