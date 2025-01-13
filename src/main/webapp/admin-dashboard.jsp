<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        .dashboard-container {
            max-width: 800px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            margin: 10px 0;
            text-align: center;
        }

        a {
            text-decoration: none;
            color: #007BFF;
            font-size: 18px;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <h1>Welcome, Admin!</h1>
    <ul>
        <li><a href="admin/rooms?action=list">Room Management</a></li>
        <li><a href="view-reservations">View Reservations</a></li>
        <li><a href="admin/statistics?action=list">View Statistics</a></li>
    </ul>
</div>
</body>
</html>
