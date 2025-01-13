<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Statistics</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
    }

    .statistics-container {
      max-width: 900px;
      margin: 50px auto;
      background: white;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    h1, h2 {
      color: #333;
    }

    h1 {
      text-align: center;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    table, th, td {
      border: 1px solid #ddd;
    }

    th, td {
      padding: 10px;
      text-align: center;
    }

    th {
      background-color: #f2f2f2;
    }

    a {
      text-decoration: none;
      background-color: #007BFF;
      color: white;
      padding: 10px 20px;
      border-radius: 5px;
      display: inline-block;
      margin-top: 20px;
    }

    a:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="statistics-container">
  <h1>Admin Statistics</h1>

  <h2>Total Reservations</h2>
  <p><%= request.getAttribute("totalReservations") %></p>

  <h2>Most Booked Rooms</h2>
  <table>
    <thead>
    <tr>
      <th>Room ID</th>
      <th>Number of Reservations</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Object[]> mostBookedRooms = (List<Object[]>) request.getAttribute("mostBookedRooms");
      if (mostBookedRooms != null && !mostBookedRooms.isEmpty()) {
        for (Object[] room : mostBookedRooms) {
    %>
    <tr>
      <td><%= room[0] %></td>
      <td><%= room[1] %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="2">No data available.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <h2>Reservations Per Day</h2>
  <table>
    <thead>
    <tr>
      <th>Date</th>
      <th>Number of Reservations</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Object[]> reservationsPerDay = (List<Object[]>) request.getAttribute("reservationsPerDay");
      if (reservationsPerDay != null && !reservationsPerDay.isEmpty()) {
        for (Object[] day : reservationsPerDay) {
    %>
    <tr>
      <td><%= day[0] %></td>
      <td><%= day[1] %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="2">No data available.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <h2>Reservations by User</h2>
  <table>
    <thead>
    <tr>
      <th>User ID</th>
      <th>Number of Reservations</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Object[]> reservationsByUser = (List<Object[]>) request.getAttribute("reservationsByUser");
      if (reservationsByUser != null && !reservationsByUser.isEmpty()) {
        for (Object[] user : reservationsByUser) {
    %>
    <tr>
      <td><%= user[0] %></td>
      <td><%= user[1] %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="2">No data available.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>

  <a href="../admin-dashboard.jsp">← Back to Dashboard</a>
</div>
</body>
</html>
