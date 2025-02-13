# Network-Game
"Flag Match" is an interactive educational game designed for a multiplayer experience. The game aims to test players' knowledge of world flags through multiple-choice questions.

🚀 How to Run Flag Match Game on Your Device?
This game uses Java Sockets, requiring the server to be started first, followed by multiple clients connecting to it.

1️⃣ Prerequisites
🔹 Ensure you have installed:
✅ Java JDK 8 or later – Download from Oracle JDK or use OpenJDK.
✅ Git – (Optional, only if you want to clone the project directly).

2️⃣ Download the Project
You can download the code from GitHub using one of the following methods:

🔹 Option 1: Clone the repository (Recommended)
Open Git Bash or CMD and enter the following command:
bash
نسخ
تحرير
git clone https://github.com/layanAlmadi/Network-Game.git
Then navigate to the project folder:

bash
نسخ
تحرير
cd Network-Game
🔹 Option 2: Download the code manually
1️⃣ Go to GitHub Repository.
2️⃣ Click Code, then select Download ZIP.
3️⃣ Extract the ZIP file.
4️⃣ Open the terminal and navigate to the project folder.

3️⃣ Start the Server
1️⃣ Navigate to the folder where Server.java is located.
2️⃣ Compile the code using:

bash
نسخ
تحرير
javac -d . src/networkproject3/Server.java
3️⃣ Start the server:

bash
نسخ
تحرير
java networkproject3.Server
✅ If the server starts successfully, you will see a message like:

pgsql
نسخ
تحرير
✔ Server is running on port: 8080
📌 Note: If you're using Render or an external server, a dynamic port will be assigned instead of 8080.

4️⃣ Start the Client
1️⃣ Navigate to src/networkproject3/.
2️⃣ Compile the client Client.java file:

bash
نسخ
تحرير
javac -d . src/networkproject3/Client.java
3️⃣ Run the client:

bash
نسخ
تحرير
java networkproject3.Client
✅ If the connection is successful, you will see a message like:

nginx
نسخ
تحرير
Connected to server at 127.0.0.1:8080
📌 Note: If you are connecting to a server hosted online, use:

bash
نسخ
تحرير
java networkproject3.Client <server-ip> <port>
For example:

bash
نسخ
تحرير
java networkproject3.Client 10.0.1.23 8080
🎯 Playing the Game
🔹 Run multiple clients on different devices to play together.

