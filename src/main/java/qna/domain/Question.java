package qna.domain;

import qna.CannotDeleteException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private Answers answers = new Answers();

    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question(long id, String title, String contents) {
        super(id);
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public User getWriter() {
        return writer;
    }

    public Question writeBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<DeleteHistory> delete(User loginUser, long questionId) throws CannotDeleteException {
        checkDeletable(loginUser);

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(setQuestionDeleted(questionId));
        deleteHistories.addAll(setAnswersDelete(loginUser));

        return deleteHistories;
    }

    private void checkDeletable(User loginUser) throws CannotDeleteException {
        checkQuestionDeletable(loginUser);
        checkAnswerDeletable(loginUser);
    }

    public void checkQuestionDeletable(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    public void checkAnswerDeletable(User loginUser) throws CannotDeleteException {
        answers.checkAnswersDeletable(loginUser);
    }

    public DeleteHistory setQuestionDeleted(long questionId) {
        this.deleted = true;

        return new DeleteHistory(ContentType.QUESTION, questionId, writer, LocalDateTime.now());
    }

    public List<DeleteHistory> setAnswersDelete(User loginUsers) {
        return answers.delete(loginUsers);
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}