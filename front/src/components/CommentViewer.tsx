import React from "react";
import useCommentSocket from "../hooks/useCommentSocket";
import SendCommentButton from "../components/SendCommentButton";

const CommentViewer: React.FC = () => {
  const comments = useCommentSocket();

  return (
    <div>
      <h2>Live Comments</h2>
      <ul>
        {comments.map((comment) => (
          <li key={comment.uuid}>
            <strong>{comment.user}</strong>: {comment.comment}
            <div style={{ fontSize: "0.8em", color: "gray" }}>
              Type: {comment.type} | Likes: {comment.likeCount}
            </div>
            <SendCommentButton comment={comment} />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CommentViewer;
