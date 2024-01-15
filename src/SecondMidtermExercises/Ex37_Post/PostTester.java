package SecondMidtermExercises.Ex37_Post;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Comment {
    String commentId;
    String content;
    String author;
    String replyToId;
    int likes;
    List<Comment> replies;
    final static Comparator<Comment> comparatorByContent = Comparator.comparing(Comment::getContent).reversed();
    final static Comparator<Comment> comparatorByLikes = Comparator.comparing(Comment::getTotalLikes).reversed();
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
        this.replies=new ArrayList<>();
    }

    public void addReply(Comment comment) {
        //replies.putIfAbsent(comment.getCommentId(), comment);
        replies.add(comment);
    }

    public void like() {
        this.likes++;
    }
    public int getTotalLikes() {
        int totalLikes= this.likes;
        for(Comment reply : replies) {
            totalLikes+= reply.getTotalLikes();
        }
        return totalLikes;
    }

    public Comment findComment(String targetId) {
        if(commentId.equals(targetId)) {
            return this;
        }
        for(Comment comment : replies) {
            Comment found = comment.findComment(targetId);
            if(found!= null) {
                return found;
            }
        }
        return null;
    }

    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        String spaces = IntStream.range(0,indent).mapToObj(i->" ").collect(Collectors.joining(""));
        sb.append(String.format("%sComment: %s\n%sWritten by: %s\n%sLikes: %d\n",
                spaces,content,spaces,author,spaces,likes));
        replies.stream().sorted(comparatorByLikes).forEach(comment -> sb.append(comment.print(indent+4)));
        return sb.toString();
    }
}

class Post {
    String username;
    String postContent;
    List<Comment> comments;

    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;
        this.comments = new ArrayList<>();
    }
    public void addComment(String author, String commendId, String content, String replyToId) {
        Comment comment = new Comment(commendId, content, author, replyToId);
        if(replyToId==null) {
            //comments.putIfAbsent(commendId, comment);
            comments.add(comment);
        } else {
            Comment target = findComment(replyToId);
            if(target!=null) {
                target.addReply(comment);
            }
        }
    }
    private Comment findComment(String commentId) {
        for(Comment comment : comments) {
            Comment found = comment.findComment(commentId);
            if(found != null) {
                return found;
            }
        }
        return null;
    }
    public void likeComment(String commentId) {
        Comment comment = findComment(commentId);
        if(comment!=null) {
            comment.like();
        }
    }
    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Post: %s\nWritten by: %s\n",postContent,username));
        sb.append("Comments:\n");
        comments.stream().sorted(Comment.comparatorByLikes).forEach(comment -> sb.append(comment.print(indent+8)));
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

