package edu.miu.cs.cs544.domain.adapter;

import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.Product;
import edu.miu.cs.cs544.domain.ProductDTO;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.dto.ItemDTO;

public class ItemAdapter {

    public static ItemDTO getItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO(item.getId(),
                item.getOccupants(),
                item.getCheckinDate(),
                item.getCheckoutDate(),
                ProductAdapter.getProductDTO(item.getProduct()),
                item.getAuditData());
        return itemDTO;
    }

    public static Item getItem(ItemDTO itemDTO) {
        Item item = new Item(itemDTO.getId(),
                itemDTO.getOccupants(),
                itemDTO.getCheckinDate(),
                itemDTO.getCheckoutDate(),
                new Reservation(),
                ProductAdapter.getProduct(itemDTO.getProductDTO()),
                itemDTO.getAuditData());
        return item;
    }
}
