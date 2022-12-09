package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.BusDto;
import kr.ac.gachon.pproject.entity.Bus;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.repository.BusRepository;
import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    private final UserService userService;

    public Bus createBus(BusDto busDto) {
        System.out.println(busDto.toString());
        boolean isUser = userService.isUser(busDto.getAppId());
        if (!isUser) {
            return null;
        }

        Bus validBus = busRepository.findByAppId(busDto.getAppId());
        Bus savedBus;
        if (validBus == null) {
            Bus bus = new Bus();
            bus.setAppId(busDto.getAppId());
            bus.setLineNumber(busDto.getLineNumber());
            bus.setStationName(busDto.getStationName());
            bus.setStationId(busDto.getStationId());
            bus.setRouteId(busDto.getRouteId());
            bus.setStaOrder(busDto.getStaOrder());

            System.out.println("bus entity");
            System.out.println(bus.toString());
            savedBus = busRepository.save(bus);
        } else {
            validBus.setLineNumber(busDto.getLineNumber());
            validBus.setStationName(busDto.getStationName());
            validBus.setStationId(busDto.getStationId());
            validBus.setRouteId(busDto.getRouteId());
            validBus.setStaOrder(busDto.getStaOrder());

            savedBus = busRepository.save(validBus);
        }
        return savedBus;
    }

    public Bus getBusInfo(String appId) {
        Bus bus = busRepository.findByAppId(appId);
        return bus;
    }

    public String getApiXml(String url) {
        System.out.println(url);
        URI uri = URI.create(url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.add("Content-Type", "application/xml;charset=UTF-8");
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class);
//        String res = restTemplate.getForObject(uri, String.class);

        System.out.println(res.getBody());
        return res.getBody();
    }

    public List xmlToJson(String sXmlData, String sNodeName) throws Exception{
        List list = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        String rAttribute = "";
        try {
            // XML 문서 파싱
            InputSource is = new InputSource(new StringReader(sXmlData));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);

            Element root = doc.getDocumentElement();  //Get Root Node
//            System.out.println("root: " + root.getNodeName());
            NodeList children = root.getChildNodes(); // 자식 노드 목록 get

            for(int i = 0; i < children.getLength(); i++){
                Node node = children.item(i);
//                System.out.println("===== print text content =====");
//                System.out.println(node.getTextContent());
//                System.out.println(node.getNodeName());
//                System.out.println("==============================");
                if (node.getNodeName() == "msgBody") {
                    NodeList topLevelNode = node.getChildNodes();
                    for (int j = 0; j < topLevelNode.getLength(); j++){
                        Node parent = topLevelNode.item(j);
//                        System.out.println("in msgBody: " + parent.getNodeName());
                        NodeList childNodes = parent.getChildNodes();
//                        System.out.println("childNodes length: " + childNodes.getLength());
                        // 필수 value
                        HashMap map = new HashMap<>();
                        for (int k = 0; k < childNodes.getLength(); k++){
                            Node valueNode = childNodes.item(k);
                            String codeNm = valueNode.getNodeName().toString();
                            String value = valueNode.getTextContent().toString();
                            map.put(codeNm, value);
//                            System.out.println("-------------------------");
//                            System.out.println("type: " + valueNode.getNodeName() + " / " + valueNode.getNodeName().getClass().getName());
//                            System.out.println("value: " + valueNode.getTextContent() + " / " + valueNode.getTextContent().getClass().getName());
//                            System.out.println("-------------------------");
                        }
                        list.add(map);
                    }
                }
//                if(node.getNodeType() == Node.ELEMENT_NODE && sNodeName.equals(node.getNodeName())) {
//                    Element ele = (Element)node;
//                    rAttribute = ele.getAttribute("attr");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        System.out.println("======= result =======");
        System.out.println(list);
        return list;
    }
}
