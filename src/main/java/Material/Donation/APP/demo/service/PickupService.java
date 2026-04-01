package Material.Donation.APP.demo.service;


import Material.Donation.APP.demo.repository.PickupRepository;
import Material.Donation.APP.demo.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;
    private final RequestRepository requestRepository;

    public PickupResponseDTO createPickup(PickupRequestDTO dto) {
        Request request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Pickup pickup = Pickup.builder()
                .request(request)
                .scheduledAt(dto.getScheduledAt())
                .pickupAddress(dto.getPickupAddress())
                .status("scheduled")
                .build();

        Pickup saved = pickupRepository.save(pickup);
        return mapToDTO(saved);
    }

    public List<PickupResponseDTO> getAllPickups() {
        return pickupRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PickupResponseDTO getPickupById(String id) {
        Pickup pickup = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pickup not found"));
        return mapToDTO(pickup);
    }

    public PickupResponseDTO updatePickupStatus(String id, String status) {
        Pickup pickup = pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pickup not found"));

        pickup.setStatus(status);

        if ("completed".equals(status)) {
            pickup.setCompletedAt(java.time.LocalDateTime.now());
        }

        return mapToDTO(pickupRepository.save(pickup));
    }

    private PickupResponseDTO mapToDTO(Pickup pickup) {
        PickupResponseDTO dto = new PickupResponseDTO();
        dto.setId(pickup.getId());
        dto.setRequestId(pickup.getRequest().getId());
        dto.setScheduledAt(pickup.getScheduledAt());
        dto.setPickupAddress(pickup.getPickupAddress());
        dto.setStatus(pickup.getStatus());
        dto.setCompletedAt(pickup.getCompletedAt());
        return dto;
    }
}