package Bendispository.Abschlussprojekt.model;

import Bendispository.Abschlussprojekt.model.transactionModels.MarketType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

@Entity
@ToString(exclude = "owner")
@Data
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Pattern(regexp="[a-zA-ZöäüÖÄÜß 0-9]+")
	private String name;

	private String description;

	private MarketType marketType;

	private boolean active = true;

	//@Pattern(regexp="[0-9.,]+")
	private double retailPrice;

	@Digits(integer = 100, fraction = 0)
	private int deposit;

	@Embedded
	private UploadFile uploadFile;

	@Digits(integer = 10, fraction = 0)
	private int costPerDay;

	@Pattern(regexp="[a-zA-ZöäüÖÄÜß 0-9]+")
	private String place;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Person owner;
}
