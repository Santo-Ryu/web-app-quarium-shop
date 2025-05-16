class WebSocketService {
  constructor(url) {
    this.url = url;
    this.ws = null;
    this.listeners = new Set();
  }

  connect() {
    if (this.ws) {
      return; // Đã kết nối rồi
    }

    this.ws = new WebSocket(this.url);

    this.ws.onopen = () => {
      console.log("WebSocket connected");
    };

    this.ws.onmessage = (event) => {
      this.listeners.forEach((callback) => callback(event.data));
    };

    this.ws.onclose = () => {
      console.log("WebSocket disconnected");
      this.ws = null;
    };

    this.ws.onerror = (error) => {
      console.error("WebSocket error", error);
    };
  }

  sendMessage(message) {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      this.ws.send(message);
    } else {
      console.warn("WebSocket not connected");
    }
  }

  onMessage(callback) {
    this.listeners.add(callback);
  }

  offMessage(callback) {
    this.listeners.delete(callback);
  }

  disconnect() {
    if (this.ws) {
      this.ws.close();
      this.ws = null;
    }
  }
}

export default WebSocketService;
