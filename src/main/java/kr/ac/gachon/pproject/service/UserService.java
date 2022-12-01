package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.UserDto;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDto userDto) {
        User user = new User();
        user.setMacId(userDto.getMacId());
        user.setAppId(userDto.getAppId());
        return user;
    }

    public boolean isUser(String appId) {
        User user = this.userRepository.findByAppId(appId);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public User connectFriday(User user) {
        User findUser = userRepository.findByMacId(user.getMacId());
        if (findUser == null || findUser.getAppId() != null) {
            return null;
        } else {
            findUser.setAppId(user.getAppId());
            userRepository.save(findUser);
            return findUser;
        }
    }
}
