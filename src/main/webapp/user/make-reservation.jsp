<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<!DOCTYPE html>
<html>
<head>
  <title>Make a Reservation</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 20px;
    }

    h1, h2 {
      text-align: center;
      color: #333;
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

    form {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    form label {
      font-size: 14px;
      margin-top: 5px;
    }

    form input, form button {
      margin-top: 5px;
      padding: 8px;
      border-radius: 4px;
      border: 1px solid #ddd;
    }

    form button {
      background-color: #007BFF;
      color: white;
      border: none;
      cursor: pointer;
      font-weight: bold;
    }

    form button:hover {
      background-color: #0056b3;
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

    .no-rooms {
      text-align: center;
      font-size: 16px;
      color: #666;
    }
  </style>
</head>
<body>
<h1>Make a Reservation</h1>

<!-- Success or Error Messages -->
<%
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
  // Retrieve the list of all rooms from the request
  List<Room> rooms = (List<Room>) request.getAttribute("rooms");
%>

<h2>Available Rooms</h2>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Capacity</th>
    <th>Equipment</th>
    <th>Availability</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <%
    if (rooms != null && !rooms.isEmpty()) {
      for (Room room : rooms) {
  %>
  <tr>
    <td><%= room.getId() %></td>
    <td><%= room.getName() %></td>
    <td><%= room.getCapacity() %></td>
    <td><%= room.getEquipment() %></td>
    <td><%= room.isAvailability() ? "Available" : "Not Available" %></td>
    <td>
      <!-- Submit the reservation form -->
      <form action="reserve-rooms" method="post">
        <input type="hidden" name="action" value="reserve">
        <input type="hidden" name="room" value="<%= room.getId() %>">
        <label>Date:</label>
        <input type="date" name="date" required>
        <label>Start Time:</label>
        <input type="time" name="startTime" required>
        <label>End Time:</label>
        <input type="time" name="endTime" required>
        <button type="submit" <%= !room.isAvailability() ? "disabled" : "" %>>Reserve</button>
      </form>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="6" class="no-rooms">No rooms available</td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

<a href="user-dashboard.jsp">Back to Dashboard</a>
</body>
</html>
