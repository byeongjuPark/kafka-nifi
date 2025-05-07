import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { CommentData } from "../types/CommentData";

const useCommentSocket = (): CommentData[] => {
  const [comments, setComments] = useState<CommentData[]>([]);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = new Client({
      webSocketFactory: () => socket as WebSocket,
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe("/comments/comment", (message) => {
          const data = JSON.parse(message.body) as CommentData[];
          setComments(data);
        });
      },
      onStompError: (frame) => {
        console.error("[WebSocket] STOMP Error", frame);
      },
    });

    client.activate();

    return () => {
      if (client.active) client.deactivate();
    };
  }, []);

  return comments;
};

export default useCommentSocket;
