import React from "react";
import CommentViewer from "../components/CommentViewer";
import NewsComment from "../components/NewsComment";
import BlogComment from "../components/BlogComment";

const Home = () => {
  return (
    <div>
      <div style={{ display: "flex", gap: "100px" }}>
        <div>
          <h1>뉴스 댓글</h1>
          <NewsComment />
        </div>
        <div>
          <h1>블로그 댓글</h1>
          <BlogComment />
        </div>
      </div>
      <h1>실시간 댓글</h1>
      <CommentViewer />
    </div>
  );
};

export default Home;
