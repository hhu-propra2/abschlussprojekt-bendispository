package Bendispository.Abschlussprojekt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST,
               fetch = FetchType.EAGER)
    private Person requester;

    @ManyToOne(cascade = CascadeType.PERSIST,
               fetch = FetchType.EAGER)
    private Item requestedItem;

    private int duration;

    // value = "denied", "accepted", "pending"
    private RequestStatus status = RequestStatus.PENDING;

}