package com.example.bankcards.util;

import com.example.bankcards.dto.BlockingResponseDTO;
import com.example.bankcards.entity.BlockingRequest;

public class BlockingRequestMapper {

    public static BlockingResponseDTO toDTO(BlockingRequest blockingRequest) {
        BlockingResponseDTO blockingResponseDTO = new BlockingResponseDTO();
        blockingResponseDTO.setId(String.valueOf(blockingRequest.getId()));
        String fp = blockingRequest.getCard().getCardNumber().substring(0, 15);
        String sp = blockingRequest.getCard().getCardNumber().substring(15);
        fp = fp.replaceAll("\\d", "*");
        blockingResponseDTO.setCardNumber(fp + sp);
        blockingResponseDTO.setCardHolderEmail(blockingRequest.getUser().getEmail());

        return blockingResponseDTO;
    }

}
