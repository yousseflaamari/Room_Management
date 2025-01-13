<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Reservations</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .back-button {
            margin: 20px 0;
        }
        .back-button a {
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 16px;
        }
        .back-button a:hover {
            background-color: #45a049;
        }
        .success-message {
            color: green;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin-bottom: 20px;
        }
        button {
            background-color: #008CBA;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #007BB5;
        }
    </style>
</head>
<body>
<h1>Room Reservations</h1>

<!-- Functional Go Back Button -->
<div class="back-button">
    <a href="admin-dashboard.jsp">← Go Back to Menu</a>
</div>

<!-- Success or Error Messages -->
<c:if test="${not empty success}">
    <div class="success-message">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Status</th>
        <th>User</th>
        <th>Room</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="reservation" items="${reservations}">
        <tr>
            <td>${reservation[0]}</td> <!-- ID -->
            <td>${reservation[1]}</td> <!-- Date -->
            <td>${reservation[2]}</td> <!-- Start Time -->
            <td>${reservation[3]}</td> <!-- End Time -->
            <td>${reservation[4]}</td> <!-- Status -->
            <td>${reservation[5]}</td> <!-- User Name -->
            <td>${reservation[6]}</td> <!-- Room Name -->
            <td>
                <!-- Admin can update the status -->
                <form action="view-reservations" method="post">
                    <input type="hidden" name="id" value="${reservation[0]}"> <!-- Reservation ID -->
                    <input type="hidden" name="date" value="${reservation[1]}"> <!-- Date -->
                    <input type="hidden" name="start_time" value="${reservation[2]}"> <!-- Start Time -->
                    <input type="hidden" name="end_time" value="${reservation[3]}"> <!-- End Time -->
                    <input type="hidden" name="room_id" value="${reservation[6]}"> <!-- Room ID -->
                    <input type="hidden" name="user_id" value="${reservation[5]}"> <!-- User ID -->
                    <select name="status">
                        <option value="Confirmed" ${reservation[4] == 'Confirmed' ? 'selected' : ''}>Confirmed</option>
                        <option value="Pending" ${reservation[4] == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Rejected" ${reservation[4] == 'Rejected' ? 'selected' : ''}>Rejected</option>
                    </select>
                    <button type="submit">Update</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
