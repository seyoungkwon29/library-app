package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    // 사용자 수정
    public void updateName(String name) {
        this.name = name;
    }

    // 책 대출
    public void loanBook(String bookName) {
        userLoanHistories.add(new UserLoanHistory(this, bookName, false));
    }

    // 책 반납
    public void returnBook(String bookName) {
        UserLoanHistory target = this.userLoanHistories.stream()
                .filter(userLoanHistory -> userLoanHistory.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        target.doReturn();
    }
}
