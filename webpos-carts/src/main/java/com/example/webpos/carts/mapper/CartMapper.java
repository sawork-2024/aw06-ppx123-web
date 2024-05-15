package com.example.webpos.carts.mapper;


import com.example.webpos.carts.model.Cart;
import com.example.webpos.carts.model.Item;
import org.mapstruct.Mapper;
import com.example.webpos.api.rest.model.*;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CartMapper {
    List<CartDto> toCartDtos(List<Cart> carts);

    List<Cart> toCarts(List<CartDto> cartDtos);

    default Cart toCart(CartDto cartDto) {
        return new Cart().id(cartDto.getId()).items(toItems(cartDto.getItems(), cartDto));
    }

    default CartDto toCartDto(Cart cart) {
        return new CartDto().id(cart.id()).items(toItemDtos(cart.items()));
    }

    default List<CartItemDto> toItemDtos(List<Item> items) {
        return items.stream().map(this::toItemDto).collect(Collectors.toList());
    }

    default  List<Item> toItems(List<CartItemDto> itemDtos, CartDto cartDto) {
        if (itemDtos == null)
            return null;
        return itemDtos.stream().map(itemDto -> toItem(itemDto, cartDto)).collect(Collectors.toList());
    }

    default CartItemDto toItemDto(Item item) {
        return new CartItemDto().id(item.id()).amount(item.quantity()).product(getProductDto(item));
    }

    default Item toItem(CartItemDto itemDto, CartDto cartDto) {
        return new Item().id(itemDto.getId()).cartId(cartDto.getId()).productId(itemDto.getProduct().getId())
                .productName(itemDto.getProduct().getName()).quantity(itemDto.getAmount()).price(itemDto.getProduct().getPrice());
    }


    default ProductDto getProductDto(Item item) {
        return new ProductDto().id(item.productId()).name(item.productName()).price(item.price());
    }

}
