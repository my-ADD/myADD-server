package com.myadd.myadd.user.sigunup.email.change.password.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "EmailAuth")
@Entity
public class EmailAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String authNum;

    private LocalDateTime authNumTimestamp;
}
