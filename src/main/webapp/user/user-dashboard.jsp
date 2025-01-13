<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>User Dashboard</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
    }

    .dashboard-container {
      max-width: 800px;
      margin: 50px auto;
      background: #ffffff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    h1 {
      text-align: center;
      color: #333;
      font-size: 24px;
      margin-bottom: 20px;
    }

    ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    li {
      text-align: center;
      margin-bottom: 15px;
    }

    a {
      text-decoration: none;
      color: #007BFF;
      font-size: 18px;
      font-weight: bold;
    }

    a:hover {
      text-decoration: underline;
    }

    .logout {
      text-align: center;
      margin-top: 20px;
    }

    .logout a {
      color: #dc3545;
      font-size: 16px;
      font-weight: bold;
    }

    .logout a:hover {
      color: #c82333;
    }
  </style>
</head>
<body>
<div class="dashboard-container">
  <h1>Welcome, ${sessionScope.userName}</h1> <!-- Display logged-in user's name -->
  <ul>
    <li><a href="reserve-rooms?action=list">Make a Reservation</a></li>
    <li><a href="view-reservations?action=list">View My Reservations</a></li>
  </ul>
  <div class="logout">
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
  </div>
</div>
</body>
</html>
