package me.joaocansi.terraboosters.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DatabaseTable(tableName = "boosters")
@NoArgsConstructor
@Getter
@Setter
public class Booster {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String boosterId;
    @DatabaseField(canBeNull = false)
    private String playerId;
    @DatabaseField(canBeNull = false)
    private long duration;
}
