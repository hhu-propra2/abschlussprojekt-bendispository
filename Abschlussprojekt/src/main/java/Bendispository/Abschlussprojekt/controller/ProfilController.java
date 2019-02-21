package Bendispository.Abschlussprojekt.controller;

import Bendispository.Abschlussprojekt.model.Item;
import Bendispository.Abschlussprojekt.model.Person;
import Bendispository.Abschlussprojekt.model.transactionModels.LeaseTransaction;
import Bendispository.Abschlussprojekt.model.transactionModels.ProPayAccount;
import Bendispository.Abschlussprojekt.repos.ItemRepo;
import Bendispository.Abschlussprojekt.repos.PersonsRepo;
import Bendispository.Abschlussprojekt.repos.RequestRepo;
import Bendispository.Abschlussprojekt.repos.transactionRepos.LeaseTransactionRepo;
import Bendispository.Abschlussprojekt.service.AuthenticationService;
import Bendispository.Abschlussprojekt.service.ProPaySubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfilController {

    private ItemRepo itemRepo;
    private PersonsRepo personRepo;
    private RequestRepo requestRepo;
    private LeaseTransactionRepo leaseTransactionRepo;
    private AuthenticationService authenticationService;

    @Autowired
    public ProfilController(ItemRepo itemRepo,
                            PersonsRepo personsRepo,
                            RequestRepo requestRepo,
                            LeaseTransactionRepo leaseTransactionRepo,
                            AuthenticationService authenticationService){
        this.itemRepo = itemRepo;
        this.personRepo = personsRepo;
        this.requestRepo = requestRepo;
        this.leaseTransactionRepo = leaseTransactionRepo;
        this.authenticationService = authenticationService;
        this.leaseTransactionRepo = leaseTransactionRepo;
    }

    @GetMapping(path= "/")
    public String Overview(Principal principal, Model model){
        Person loggedIn = authenticationService.getCurrentUser();
        List<Item> allOtherItems = itemRepo.findByOwnerNot(personRepo.findByUsername(loggedIn.getUsername()));
        model.addAttribute("OverviewAllItems", allOtherItems);
        model.addAttribute("loggedInPerson",loggedIn);
        return "overviewAllItems";
    }

    @GetMapping(path= "/profile")
    public String profile(Model model){
        Person loggedIn = authenticationService.getCurrentUser();
        model.addAttribute("person", loggedIn);

        ProPaySubscriber proPaySubscriber = new ProPaySubscriber(personRepo, leaseTransactionRepo);

        ProPayAccount proPayAccount = proPaySubscriber.getAccount(loggedIn.getUsername(), ProPayAccount.class);
        model.addAttribute("account", proPayAccount);
        model.addAttribute("reservations", proPayAccount.getReservations());
        return "profile";
    }

    @GetMapping(path = "/profile/history")
    public String history(Model model){
        Person loggedIn = authenticationService.getCurrentUser();
        List<LeaseTransaction> leased =
                leaseTransactionRepo
                        .findAllByLeaserAndLeaseIsConcludedIsTrue(loggedIn);
        List<LeaseTransaction> lent =
                leaseTransactionRepo
                        .findAllByItemOwnerAndLeaseIsConcludedIsTrue(loggedIn);
        model.addAttribute("leased", leased);
        model.addAttribute("lent", lent);
        return "historia";
    }

    @GetMapping(path= "/profile/{id}")
    public String profileOther(Model model,
                               @PathVariable Long id){
        Optional<Person> person = personRepo.findById(id);
        personRepo.findById(id).ifPresent(o -> model.addAttribute("person",o));
        return "profileOther";
    }

    @GetMapping(path= "/profilub")
    public String profilPage(Model model){
        List<Person> all = personRepo.findAll();
        model.addAttribute("personen", all);
        model.addAttribute("loggedInPerson", authenticationService.getCurrentUser());
        return "profileDetails";
    }



    @GetMapping(value="deleteUser/{username}")
    public String deleteUser(@PathVariable String username){
        Person deletePerson = personRepo.findByUsername(username);
        personRepo.delete(deletePerson);
        return "redirect:/profilub";
    }
}