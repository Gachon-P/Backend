package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.UserDto;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.repository.UserRepository;
import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User testUser() {
        User user = userRepository.findByMacId(Constant.FRIDAY_MAC);
        return user;
    }
    public User createUser(UserDto userDto) {
        this.testFunc();
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

    public void testFunc() {
        String mac = Constant.FRIDAY_MAC;
        User findUser = userRepository.findByMacId(mac);
        if (findUser == null) {
            User user = new User();
            user.setMacId(mac);
            userRepository.save(user);
        }
    }

    public String macIdToAppId(String macId) {
        User user = userRepository.findByMacId(macId);
        if (user == null) {
            return null;
        } else {
            return user.getAppId();
        }
    }
}
