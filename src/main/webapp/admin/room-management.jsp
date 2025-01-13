<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<!DOCTYPE html>
<html>
<head>
  <title>Manage Rooms</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f9f9f9;
      color: #333;
    }
    h1, h2 {
      text-align: center;
      color: #4CAF50;
    }
    form {
      max-width: 500px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }
    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    input[type="text"],
    input[type="number"],
    input[type="checkbox"] {
      width: 100%;
      padding: 8px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 14px;
      display: block;
      width: 100%;
    }
    button:hover {
      background-color: #45a049;
    }
    table {
      width: 90%;
      margin: 20px auto;
      border-collapse: collapse;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
      overflow: hidden;
    }
    table, th, td {
      border: 1px solid #ddd;
    }
    th, td {
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    tr:nth-child(even) {
      background-color: #f2f2f2;
    }
    .back-button {
      text-align: center;
      margin-top: 20px;
    }
    .back-button a {
      text-decoration: none;
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border-radius: 5px;
      font-size: 14px;
    }
    .back-button a:hover {
      background-color: #45a049;
    }
    .actions form {
      display: inline;
    }
    .actions button {
      margin: 5px;
      width: auto;
    }
  </style>
</head>
<body>
<h1>Room Management</h1>

<%
  Room roomToEdit = (Room) request.getAttribute("room");
%>

<h2><%= roomToEdit != null ? "Edit Room" : "Add a New Room" %></h2>
<form action="rooms" method="post">
  <input type="hidden" name="action" value="<%= roomToEdit != null ? "update" : "add" %>">
  <% if (roomToEdit != null) { %>
  <input type="hidden" name="id" value="<%= roomToEdit.getId() %>">
  <% } %>
  <label for="name">Room Name:</label>
  <input type="text" id="name" name="name" value="<%= roomToEdit != null ? roomToEdit.getName() : "" %>" required>

  <label for="capacity">Capacity:</label>
  <input type="number" id="capacity" name="capacity" value="<%= roomToEdit != null ? roomToEdit.getCapacity() : "" %>" required>

  <label for="equipment">Equipment:</label>
  <input type="text" id="equipment" name="equipment" value="<%= roomToEdit != null ? roomToEdit.getEquipment() : "" %>" required>

  <label for="availability">Available:</label>
  <input type="checkbox" id="availability" name="availability" value="true" <%= roomToEdit != null && roomToEdit.isAvailability() ? "checked" : "" %>>

  <button type="submit"><%= roomToEdit != null ? "Update Room" : "Add Room" %></button>
</form>

<hr>

<h2>Existing Rooms</h2>
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
    List<Room> rooms = (List<Room>) request.getAttribute("rooms");
    if (rooms != null && !rooms.isEmpty()) {
      for (Room room : rooms) {
  %>
  <tr>
    <td><%= room.getId() %></td>
    <td><%= room.getName() %></td>
    <td><%= room.getCapacity() %></td>
    <td><%= room.getEquipment() %></td>
    <td><%= room.isAvailability() ? "Available" : "Not Available" %></td>
    <td class="actions">
      <form action="rooms" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="<%= room.getId() %>">
        <button type="submit" style="background-color: #f44336;">Delete</button>
      </form>
      <form action="rooms" method="get">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="<%= room.getId() %>">
        <button type="submit" style="background-color: #008CBA;">Edit</button>
      </form>
    </td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="6">No rooms available</td>
  </tr>
  <% } %>
  </tbody>
</table>

<div class="back-button">
  <a href="../admin-dashboard.jsp">← Go Back to Menu</a>
</div>
</body>
</html>
