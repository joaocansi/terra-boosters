package me.joaocansi.terraboosters.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Setter @Getter
public class BoosterProduct {
    private String id;
    private String name;
    private String skill;
    private ItemStack item;
    private long duration;
    private float multiplication;
}
