package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.BusDto;
import kr.ac.gachon.pproject.entity.Bus;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final UserService userService;

    public Bus createBus(BusDto busDto) {
        boolean isUser = userService.isUser(busDto.getAppId());
        if (!isUser) {
            return null;
        }
        Bus bus = new Bus();
        bus.setAppId(busDto.getAppId());
        bus.setLineCode(busDto.getLineCode());
        bus.setStationCode(busDto.getStationCode());

        Bus savedBus = busRepository.save(bus);
        return savedBus;
    }
}
