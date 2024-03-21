package ua.ithillel.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.app.model.User;
import ua.ithillel.app.model.dto.UserDTO;
import ua.ithillel.app.model.mapper.UserMapper;
import ua.ithillel.app.repo.RoleRepo;
import ua.ithillel.app.repo.UserRepo;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends ServiceTestParent{

    @Mock
    UserRepo userRepo;
    @Mock
    RoleRepo roleRepo;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    void addUser_successful() {
        UserDTO mockUser = mockUserDTOs.get(0);

        when(userMapper.userDTOToUser(any())).thenReturn(mockUsers.get(0));
        when(userRepo.save(any())).thenReturn(mockUsers.get(0));
        when(userMapper.userToUserDTO(any())).thenReturn(mockUserDTOs.get(0));

        UserDTO result = userServiceImpl.addUser(mockUser);

        verify(userMapper, times(1)).userDTOToUser(mockUser);
        verify(userRepo, times(1)).save(any());
        verify(userMapper, times(1)).userDTOToUser(any());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result, mockUser);
    }

    @Test
    void getAllUsers_successful() {

        when(userRepo.findAll()).thenReturn(mockUsers);
        when(userMapper.userToUserDTO(any())).thenReturn(mockUserDTOs.get(0));

        List<UserDTO> allUsers = userServiceImpl.getAllUsers();

        verify(userRepo, times(1)).findAll();
        verify(userMapper, times(mockUsers.size())).userToUserDTO(any());

        assertNotNull(allUsers);
        assertThat(allUsers.size()).isEqualTo(mockUsers.size());
    }

    @Test
    void getUserById_successful() {
        User user = mockUsers.get(0);
        Long testId = user.getId();

        when(userRepo.findById(anyLong())).thenReturn(Optional.ofNullable(mockUsers.get(testId.intValue()-1)));
        when(userMapper.userToUserDTO(any())).thenReturn(mockUserDTOs.get(0));

        UserDTO userById = userServiceImpl.getUserById(testId);

        verify(userRepo, times(1)).findById(testId);
        verify(userMapper, times(1)).userToUserDTO(any());

        assertNotNull(userById);
        assertEquals(userById.getId(), testId);
    }

    @Test
    void deleteUser_successful() {
        User user = mockUsers.get(0);
        Long testId = user.getId();

        when(userRepo.save(any())).thenReturn(mockUsers.get(0));
        when(userRepo.findById(testId)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(mockUserDTOs.get(testId.intValue()-1));


        UserDTO userDTO = userServiceImpl.deleteUser(testId);

        verify(userRepo, times(1)).save(user);
        verify(userRepo, times(1)).findById(testId);
        verify(userRepo, times(1)).deleteById(testId);

        assertNotNull(userDTO);
        assertEquals(userDTO.getId(), testId);
    }

    @Test
    void editUser_successful() {
        UserDTO userDTO = mockUserDTOs.get(0);
        Long testId = 1L;

        when(userRepo.findById(testId)).thenReturn(Optional.of(mockUsers.get(0)));
        when(userRepo.save(any())).thenReturn(mockUsers.get(0));
        when(userMapper.userToUserDTO(any())).thenReturn(mockUserDTOs.get(0));

        UserDTO editUser = userServiceImpl.editUser(testId, userDTO);

        verify(userRepo, times(1)).findById(testId);
        verify(userRepo, times(1)).save(mockUsers.get(0));

        assertNotNull(editUser);
    }

    @Test
    void setUserRole_ifUserHasRole() {
        Long userId = 1L;
        Long roleId = 1L;
        User user = mockUsers.get(0);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepo.findById(roleId)).thenReturn(Optional.of(mockRoles.get(0)));
        when(userMapper.userToUserDTO(user)).thenReturn(mockUserDTOs.get(0));

        UserDTO setUserRole = userServiceImpl.setUserRole(userId, roleId);

        verify(userRepo, times(1)).findById(userId);
        verify(roleRepo, times(1)).findById(roleId);
        verify(userMapper, times(1)).userToUserDTO(user);

        assertNotNull(setUserRole);
    }

    @Test
    void setUserRole_ifUserDontHasRole() {
        Long userId = 1L;
        Long roleId = 2L;
        User user = mockUsers.get(0);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepo.findById(roleId)).thenReturn(Optional.of(mockRoles.get(1)));
        when(userRepo.save(user)).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(mockUserDTOs.get(0));

        UserDTO setUserRole = userServiceImpl.setUserRole(userId, roleId);

        verify(userRepo, times(1)).findById(userId);
        verify(roleRepo, times(1)).findById(roleId);
        verify(userRepo, times(1)).save(user);
        verify(userMapper, times(1)).userToUserDTO(user);

        assertNotNull(setUserRole);
    }

    @Test
    void removeUserRole_successful() {
        Long userId = 1L;
        Long roleId = 1L;
        User user = mockUsers.get(0);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(mockUserDTOs.get(0));

        UserDTO setUserRole = userServiceImpl.removeUserRole(userId, roleId);

        verify(userRepo, times(1)).findById(userId);
        verify(userRepo, times(1)).save(user);
        verify(userMapper, times(1)).userToUserDTO(user);

        assertNotNull(setUserRole);
    }
}
