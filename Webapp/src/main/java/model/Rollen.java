package model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

@Entity
@Named
@RequestScoped
public enum Rollen {
	TESTER,
	TESTERSTELLER,
	RE,
	TESTMANAGER
}