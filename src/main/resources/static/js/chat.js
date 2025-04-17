// Giả sử bạn lấy userId từ URL hoặc gắn sẵn từ server
const userId = "Quynh123"; // hoặc lấy từ biến server đẩy vào

// Kết nối socket với userId
const socket = io("http://localhost:5173", {
    query: { userId }
});

// Khi có tin nhắn mới từ người khác
socket.on("new_private_message", (message) => {
    console.log("Tin nhắn mới:", message);
    // Hiển thị ra UI (bạn tự xử lý tiếp)
});

// Gửi tin nhắn
document.querySelector("#sendButton").addEventListener("click", () => {
    const to = document.querySelector("#toUser").value;
    const content = document.querySelector("#messageInput").value;

    socket.emit("private_message", {
        from: userId,
        to,
        content
    });
});
