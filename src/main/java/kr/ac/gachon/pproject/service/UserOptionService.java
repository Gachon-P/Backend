package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.UserOptionDto;
import kr.ac.gachon.pproject.entity.UserOption;
import kr.ac.gachon.pproject.repository.UserOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserOptionService {
    private static UserOptionRepository userOptionRepository;

    public UserOption createUserOption(UserOptionDto userOptionDto) {
        UserOption userOption = userOptionRepository.findByAppId(userOptionDto.getAppId());
        if (userOption == null) {
            UserOption newUserOption = new UserOption();
            newUserOption.setAppId(userOptionDto.getAppId());
            newUserOption.setSensorStart(userOptionDto.getSensorStart());
            newUserOption.setSensorEnd(userOptionDto.getSensorEnd());

            newUserOption.setTodoKeyword(userOptionDto.getTodoKeyword());
            newUserOption.setWeatherKeyword(userOptionDto.getWeatherKeyword());
            newUserOption.setBusKeyword(userOptionDto.getBusKeyword());
            newUserOption.setNewsKeyword(userOptionDto.getNewsKeyword());

            UserOption savedUserOption = userOptionRepository.save(newUserOption);
            return savedUserOption;
        } else {
            userOption.setSensorStart(userOptionDto.getSensorStart());
            userOption.setSensorEnd(userOptionDto.getSensorEnd());

            userOption.setTodoKeyword(userOptionDto.getTodoKeyword());
            userOption.setWeatherKeyword(userOptionDto.getWeatherKeyword());
            userOption.setBusKeyword(userOptionDto.getBusKeyword());
            userOption.setNewsKeyword(userOptionDto.getNewsKeyword());

            UserOption savedUserOption = userOptionRepository.save(userOption);
            return savedUserOption;
        }
    }
}
