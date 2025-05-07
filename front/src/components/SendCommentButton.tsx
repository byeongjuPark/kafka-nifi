import React from "react";
import axios from "axios";
import type { CommentData } from "../types/CommentData";

interface SendCommentButtonProps {
  comment: CommentData;
}

const SendCommentButton: React.FC<SendCommentButtonProps> = ({ comment }) => {
  const handleClick = async () => {
    try {
      const response = await axios.post("http://localhost:8080/comment/like", comment);
      console.log("Success:", response.data);
    } catch (error) {
      console.error("Error sending comment:", error);
    }
  };

  return (
    <button onClick={handleClick} className="bg-green-500 text-white px-4 py-2 rounded">
      Like
    </button>
  );
};

export default SendCommentButton;
