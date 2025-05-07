// src/components/CommentForm.tsx
import React, { useState } from "react";
import axios from "axios";
import type { Comment } from "../types/Comment";

const CommentForm: React.FC = () => {
  const [formData, setFormData] = useState<Comment>({
    user: "",
    comment: "",
    likeCount: 0,
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "likeCount" ? Number(value) : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.post<Comment>(
        "http://localhost:8080/blog/comment",
        formData
      );
      // 성공 시 폼 데이터를 초기값으로 돌려놓기
      setFormData({ user: "", comment: "", likeCount: 0 });
    } catch (err) {
      console.error("댓글 작성 실패:", err);
      alert("댓글 작성에 실패했습니다.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="user">작성자:</label>
        <input
          id="user"
          type="text"
          name="user"
          value={formData.user}
          onChange={handleChange}
          required
        />
      </div>

      <div>
        <label htmlFor="comment">댓글 내용:</label>
        <textarea
          id="comment"
          name="comment"
          value={formData.comment}
          onChange={handleChange}
          required
        />
      </div>


      <button type="submit">댓글 전송</button>
    </form>
  );
};

export default CommentForm;
