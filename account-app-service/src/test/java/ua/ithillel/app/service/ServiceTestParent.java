package ua.ithillel.app.service;

import org.junit.jupiter.api.BeforeEach;
import ua.ithillel.app.model.Account;
import ua.ithillel.app.model.Payment;
import ua.ithillel.app.model.Role;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.AccountDTO;
import ua.ithillel.app.model.dto.PaymentDTO;
import ua.ithillel.app.model.dto.RoleDTO;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.util.MockDataTestUtil;

import java.util.List;

public class ServiceTestParent {
    protected List<UserDTO> mockUserDTOs;
    protected List<AccountDTO> mockAccountDTOs;
    protected List<PaymentDTO> mockPaymentDTOs;
    protected List<RoleDTO> mockRoleDTOs;
    protected List<User> mockUsers;
    protected List<Account> mockAccounts;
    protected List<Payment> mockPayments;
    protected List<Role> mockRoles;

    @BeforeEach
    public void parentSetUp() {
        mockUserDTOs = MockDataTestUtil.getMockItems("mocks/users.json", UserDTO.class);
        mockAccountDTOs = MockDataTestUtil.getMockItems("mocks/accounts.json", AccountDTO.class);
        mockPaymentDTOs = MockDataTestUtil.getMockItems("mocks/payments.json", PaymentDTO.class);
        mockRoleDTOs = MockDataTestUtil.getMockItems("mocks/roles.json", RoleDTO.class);

        mockUsers = MockDataTestUtil.getMockItems("mocks/users-in-db.json", User.class);
        mockAccounts = MockDataTestUtil.getMockItems("mocks/accounts-in-db.json", Account.class);
        mockPayments = MockDataTestUtil.getMockItems("mocks/payments-in-db.json", Payment.class);
        mockRoles = MockDataTestUtil.getMockItems("mocks/roles-in-db.json", Role.class);
    }
}
