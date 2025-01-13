<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Reservation" %>
<!DOCTYPE html>
<html>
<head>
  <title>My Reservations</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 20px;
    }

    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }

    .message {
      color: green;
      font-weight: bold;
      text-align: center;
      margin: 20px 0;
    }

    .error {
      color: red;
      font-weight: bold;
      text-align: center;
      margin: 20px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
      background: white;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
      overflow: hidden;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: center;
    }

    th {
      background-color: #007BFF;
      color: white;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    tr:hover {
      background-color: #f1f1f1;
    }

    .no-reservations {
      text-align: center;
      font-size: 16px;
      color: #666;
    }

    a {
      display: block;
      text-align: center;
      margin: 20px 0;
      text-decoration: none;
      color: #007BFF;
      font-size: 16px;
    }

    a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<h1>My Reservations</h1>

<%
  // Success or error messages
  String success = (String) request.getSession().getAttribute("success");
  if (success != null) {
%>
<p class="message"><%= success %></p>
<%
    request.getSession().removeAttribute("success");
  }
  String error = (String) request.getAttribute("error");
  if (error != null) {
%>
<p class="error"><%= error %></p>
<%
  }
%>

<%
  // Retrieve the list of reservations for the user from the request
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
%>

<table>
  <thead>
  <tr>
    <th>Room</th>
    <th>Date</th>
    <th>Start Time</th>
    <th>End Time</th>
    <th>Status</th>
  </tr>
  </thead>
  <tbody>
  <%
    if (reservations != null && !reservations.isEmpty()) {
      for (Reservation reservation : reservations) {
  %>
  <tr>
    <td><%= reservation.getRoomId() %></td>
    <td><%= reservation.getDate() %></td>
    <td><%= reservation.getStartTime() %></td>
    <td><%= reservation.getEndTime() %></td>
    <td><%= reservation.getStatus() %></td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="5" class="no-reservations">No reservations found</td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

<a href="user-dashboard.jsp">Back to Dashboard</a>
</body>
</html>
