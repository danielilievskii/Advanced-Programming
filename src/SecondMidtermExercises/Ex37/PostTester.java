package SecondMidtermExercises.Ex37;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Comment {
    String commentId;
    String content;
    String author;
    String replyToId;
    int likes;

    Map<String, Comment> replies;

    final static Comparator<Comment> comparatorByContent = Comparator.comparing(Comment::getContent).reversed();
    final static Comparator<Comment> comparatorByLikes = Comparator.comparing(Comment::getTotalLikes).reversed();
//            .thenComparing(comparatorByContent)
//            .thenComparing(Comment::getCommentId)
//            .thenComparing(Comment::getAuthor);

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getCommentId() {
        return commentId;
    }

    public Comment(String commentId, String content, String author, String replyToId) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.replyToId = replyToId;
        this.likes = 0;
        this.replies=new HashMap<>();
    }

    public void like() {
        this.likes++;
    }
    public int getTotalLikes() {
        int totalLikes= this.likes;
        for(Comment reply : replies.values()) {
            totalLikes+= reply.getTotalLikes();
        }
        return totalLikes;
    }

    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        String spaces = IntStream.range(0,indent).mapToObj(i->" ").collect(Collectors.joining(""));
        sb.append(String.format("%sComment: %s\n%sWritten by: %s\n%sLikes: %d\n",
                spaces,content,spaces,author,spaces,likes));
        replies.values().stream().sorted(comparatorByLikes).forEach(comment -> sb.append(comment.print(indent+4)));
        return sb.toString();
    }

}

class Post {
    String username;
    String postContent;
    Map<String, Comment> commentByIdMap;

    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;
        this.commentByIdMap = new HashMap<>();
    }

    public void addComment(String author, String commendId, String content, String replyToId) {
        Comment comment = new Comment(commendId, content, author, replyToId);
        if(replyToId==null) {
            commentByIdMap.putIfAbsent(commendId, comment);
        } else {
            addReplyToComment(comment, replyToId);
        }

    }

    private boolean addReplyToComment(Comment comment, String replyToId) {
        if(commentByIdMap.containsKey(replyToId)) {
            Comment parrentComment = commentByIdMap.get(replyToId);
            parrentComment.replies.put(comment.commentId, comment);
            return true;
        } else {
            for(Comment c : commentByIdMap.values()) {
                if(addReplyToReply(comment, replyToId, c)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean addReplyToReply(Comment comment, String replyToId, Comment currentComment) {
        Comment parentComment = currentComment.replies.get(replyToId);
        if(parentComment!=null) {
            parentComment.replies.put(comment.commentId, comment);
            return true;
        } else {
            for(Comment c : currentComment.replies.values()) {
                if(addReplyToReply(comment, replyToId, c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void likeComment(String commentId) {
        Comment comment = commentByIdMap.get(commentId);
        if(comment!=null) {
            comment.like();
        } else {
            for(Comment c : commentByIdMap.values()) {
                if(likeReplyToComment(commentId, c)) {
                    break;
                }
            }
        }
    }

    private boolean likeReplyToComment(String commentId, Comment currentComment) {
        Comment comment = currentComment.replies.get(commentId);
            if(comment!=null) {
                comment.like();
                return true;
            } else {
                for(Comment c : currentComment.replies.values()) {
                    if(likeReplyToComment(commentId, c)){
                        return true;
                    }
                }
            }
        return false;
    }

    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Post: %s\nWritten by: %s\n",postContent,username));
        sb.append("Comments:\n");
        commentByIdMap.values().stream().sorted(Comment.comparatorByLikes).forEach(comment -> sb.append(comment.print(indent+8)));
        return sb.toString();
    }

    @Override
    public String toString() {
        return print(0);
    }
}


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}

