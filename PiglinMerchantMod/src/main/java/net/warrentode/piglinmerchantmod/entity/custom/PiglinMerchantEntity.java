package net.warrentode.piglinmerchantmod.entity.custom;

import com.mojang.datafixers.util.*;
import com.mojang.logging.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.boss.wither.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.*;
import net.minecraft.world.entity.monster.piglin.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.*;
import net.tslat.smartbrainlib.api.*;
import net.tslat.smartbrainlib.api.core.*;
import net.tslat.smartbrainlib.api.core.behaviour.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.*;
import net.tslat.smartbrainlib.api.core.sensor.*;
import net.tslat.smartbrainlib.api.core.sensor.custom.*;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.*;
import net.tslat.smartbrainlib.util.*;
import net.warrentode.piglinmerchantmod.client.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.bartering.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.celebrate.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.combat.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.food.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.hunting.*;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.vanilla.GoToWantedItem;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.vanilla.InteractWithDoor;
import net.warrentode.piglinmerchantmod.entity.ai.behaviors.vanilla.SetWalkTargetAwayFrom;
import net.warrentode.piglinmerchantmod.entity.ai.sensors.custom.*;
import net.warrentode.piglinmerchantmod.entity.ai.sensors.vanilla.*;
import net.warrentode.piglinmerchantmod.util.*;
import org.jetbrains.annotations.*;
import org.slf4j.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.*;
import software.bernie.geckolib.util.*;

import javax.annotation.Nullable;
import java.util.*;

public class PiglinMerchantEntity extends PathfinderMob implements SmartBrainOwner<PiglinMerchantEntity>, InventoryCarrier, Npc, GeoEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> DATA_IS_DANCING =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CANNOT_HUNT =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_WILLING_TO_BARTER =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_ITEM =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CAN_WALK =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_EATING =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_EATING_TIME =
            SynchedEntityData.defineId(PiglinMerchantEntity.class, EntityDataSerializers.INT);
    public static final UniformInt TIME_BETWEEN_HUNTS = TimeUtil.rangeOfSeconds(30, 120);
    public static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(5, 20);
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final float CHANCE_OF_WEARING_EACH_ARMOUR_ITEM = 0.1F;
    private static final double PROBABILITY_OF_SPAWNING_WITH_AXE_INSTEAD_OF_SWORD = 0.5D;
    private static final int MAX_HEALTH = 50;
    private static final double MOVEMENT_SPEED = 0.25;
    private static final double MAX_FOLLOW_RANGE = 32.0D;
    private static final double KNOCKBACK_RESISTANCE = 1;
    private static final double ATTACK_KNOCKBACK = 1;
    private static final double ATTACK_DAMAGE = 7.0D;
    private static final int MELEE_ATTACK_COOLDOWN = 0;
    private static final float COMBAT_SPEED = 1.0F;
    public static final float MIN_AVOID_DISTANCE = 6.0F;
    public static final float DESIRED_AVOID_DISTANCE = 12.0F;
    public static final int DESIRED_DISTANCE_FROM_REPELLENT = (int) DESIRED_AVOID_DISTANCE;
    public static final int REPELLENT_DETECTION_RANGE_HORIZONTAL = 8;
    public static final int REPELLENT_DETECTION_RANGE_VERTICAL = 4;
    private static final float SPEED_MULTIPLIER_WHEN_AVOIDING = 1.0F;
    private static final float SPEED_TO_WANTED_ITEM = 1.0F;
    private static final float SPEED_IDLE = 0.6F;
    public static final int ADMIRE_DURATION = 120;
    private static final int MAX_ADMIRE_DISTANCE = 9;
    private static final int MAX_ADMIRE_TIME_TO_REACH = 200;
    private static final int ADMIRE_DISABLE_TIME = 200;
    private static final int HIT_BY_PLAYER_MEMORY_TIMEOUT = 400;
    public static final int CELEBRATION_TIME = 300;
    private static final float PROBABILITY_OF_CELEBRATION_DANCE = 0.1F;
    public static final double PLAYER_ANGER_RANGE = 16.0D;
    public static final int ANGER_DURATION = 600;
    public static final int EAT_COOLDOWN = 200;
    public int foodLevel;
    private final int inventorySize = 8;
    public final SimpleContainer inventory = new SimpleContainer(inventorySize);
    public PiglinMerchantEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
        this.applyOpenDoorsAbility();
        this.getNavigation().setCanFloat(false);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CACTUS, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CACTUS, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.BLOCKED, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.LEAVES, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.BREACH, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.BREACH, -1.0F);
        this.xpReward = 10;
    }
    public static boolean checkPiglinMerchantSpawnRules(EntityType<PiglinMerchantEntity> piglinMerchantEntityEntityType, @NotNull LevelAccessor pLevel, MobSpawnType pSpawnType, @NotNull BlockPos pPos, RandomSource pRandom) {
        return !pLevel.getBlockState(pPos.below()).is(Blocks.NETHER_WART_BLOCK);
    }
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty,
                                        @NotNull MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag nbtTag) {
        RandomSource randomsource = pLevel.getRandom();
        if (pReason != MobSpawnType.STRUCTURE) {
            this.setItemSlot(EquipmentSlot.MAINHAND, this.createSpawnWeapon());
        }
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, nbtTag);
    }
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
    public static @NotNull AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FOLLOW_RANGE, MAX_FOLLOW_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.ATTACK_KNOCKBACK, ATTACK_KNOCKBACK).build();
    }
    private void applyOpenDoorsAbility() {
        if (GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
            ((GroundPathNavigation)this.getNavigation()).setCanPassDoors(true);
        }
    }
    public boolean isAdult() {
        return !this.isBaby();
    }
    public boolean isPreventingPlayerRest(@NotNull Player pPlayer) {
        return false;
    }
    public boolean shouldDropExperience() {
        return super.shouldDropExperience();
    }
    public int getExperienceReward() {
        return this.xpReward;
    }
    protected boolean shouldDropLoot() {
        return super.shouldDropLoot();
    }
    protected void dropCustomDeathLoot(@NotNull DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
    }
    private @NotNull ItemStack createSpawnWeapon() {
        return (double)this.random.nextFloat() < PROBABILITY_OF_SPAWNING_WITH_AXE_INSTEAD_OF_SWORD ?
                new ItemStack(Items.GOLDEN_AXE) : new ItemStack(Items.GOLDEN_SWORD);
    }
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource pRandom, @NotNull DifficultyInstance pDifficulty) {
        this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET), pRandom);
        this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE), pRandom);
        this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS), pRandom);
        this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS), pRandom);
    }
    private void maybeWearArmor(EquipmentSlot pSlot, ItemStack stack, @NotNull RandomSource pRandom) {
        if (pRandom.nextFloat() < CHANCE_OF_WEARING_EACH_ARMOUR_ITEM) {
            this.setItemSlot(pSlot, stack);
        }
    }
    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }
    @Override
    public List<ExtendedSensor<PiglinMerchantEntity>> getSensors() {
        //noinspection unchecked
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<PiglinMerchantEntity>()
                        .setPredicate((target, entity) ->
                                target instanceof Player ||
                                        target instanceof Zombie ||
                                        target instanceof Zoglin ||
                                        target instanceof ZombieHorse ||
                                        target instanceof Hoglin ||
                                        target instanceof WitherBoss ||
                                        target instanceof WitherSkeleton ||
                                        target instanceof AbstractIllager ||
                                        target instanceof AbstractPiglin ||
                                        target instanceof AbstractVillager ||
                                        target instanceof PiglinMerchantEntity),
                new NearbyHostileSensor<PiglinMerchantEntity>()
                        .setHostiles(
                                Pair.of(EntityType.DROWNED, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.HUSK, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.ZOMBIE, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.ZOMBIE_VILLAGER, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.ZOMBIFIED_PIGLIN, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.ZOMBIE_HORSE, DESIRED_AVOID_DISTANCE),
                                Pair.of(EntityType.ZOGLIN, DESIRED_AVOID_DISTANCE)
                        ),
                new HoglinHuntSensor<>(),
                new NearbyHuntingTargetSensor<>(),
                new HurtBySensor<>(),
                new NearbyBlocksSensor<>(),
                new UnreachableTargetSensor<>(),
                new NearestWantedItemSensor<>()
        );
    }
    @Override
    public BrainActivityGroup<PiglinMerchantEntity> getCoreTasks() {
        //noinspection unchecked,ConstantValue
        return BrainActivityGroup.coreTasks(
                new FleeTarget<>()
                        .speedModifier(SPEED_MULTIPLIER_WHEN_AVOIDING)
                        .fleeDistance(DESIRED_DISTANCE_FROM_REPELLENT)
                        .startCondition(pathfinderMob -> pathfinderMob.getHealth() < (pathfinderMob.getMaxHealth() / 2))
                        .whenStarting(pathfinderMob -> playRetreatSound(this.getOnPos(), this.getBlockStateOn())),
                new AvoidEntity<>()
                        .noCloserThan(MIN_AVOID_DISTANCE)
                        .stopCaringAfter(DESIRED_AVOID_DISTANCE)
                        .speedModifier(SPEED_MULTIPLIER_WHEN_AVOIDING)
                        .avoiding(livingEntity ->
                                livingEntity instanceof Zombie ||
                                        livingEntity instanceof Husk ||
                                        livingEntity instanceof Drowned ||
                                        livingEntity instanceof ZombieVillager ||
                                        livingEntity instanceof ZombifiedPiglin ||
                                        livingEntity instanceof ZombieHorse ||
                                        livingEntity instanceof Zoglin)
                        .whenStarting(pathfinderMob -> playRetreatSound(this.getOnPos(), this.getBlockStateOn())),
                new SetWalkTargetAwayFrom<>(MemoryModuleType.NEAREST_REPELLENT,
                        SPEED_MULTIPLIER_WHEN_AVOIDING, DESIRED_DISTANCE_FROM_REPELLENT, false, Vec3::atBottomCenterOf),
                new RememberDeadHuntingTarget<>(),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new InteractWithDoor(),
                new GoToWantedItem<>(piglinMerchant -> isNotHoldingWantedItemInOffHand(), SPEED_TO_WANTED_ITEM, true, MAX_ADMIRE_DISTANCE),
                new FirstApplicableBehaviour<>(
                        new StartAdmiring<>(ADMIRE_DURATION)
                                .whenStarting(pathfinderMob -> playAdmireSound(this.getOnPos(), this.getBlockStateOn())),
                        new StopAdmiringTooFar<>(MAX_ADMIRE_DISTANCE),
                        new StopAdmireTired<>(MAX_ADMIRE_TIME_TO_REACH, ADMIRE_DISABLE_TIME),
                        new StopHoldingAndBarter<>()
                )
        );
    }
    @Override
    public BrainActivityGroup<PiglinMerchantEntity> getIdleTasks() {
        // noinspection unchecked
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>()
                                .attackablePredicate((target) ->
                                        !(target instanceof PiglinMerchantEntity)
                                                && !(target instanceof AbstractPiglin)
                                                && !(target instanceof AbstractVillager)
                                                && !(target instanceof Zombie)
                                                && !(target instanceof ZombieHorse)
                                                && !(target instanceof Zoglin)
                                                && !target.isBaby()
                                )
                                .useMemory(MemoryModuleType.HURT_BY_ENTITY)
                                .useMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD)
                                .useMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN)
                                .useMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS)
                                .isAllyIf((mob, livingEntity) ->
                                        livingEntity instanceof PiglinMerchantEntity ||
                                                livingEntity instanceof AbstractPiglin ||
                                                livingEntity instanceof AbstractVillager
                                )
                                .alertAlliesWhen((mob, entity) ->
                                        entity instanceof Hoglin ||
                                                entity instanceof Player ||
                                                entity instanceof AbstractIllager ||
                                                entity instanceof WitherSkeleton ||
                                                entity instanceof WitherBoss),
                        new StartDancing<>(CELEBRATION_TIME)
                                .startCondition(livingEntity -> isDancing())
                                .whenStarting((TodePiglinMerchant) -> new MoveToWalkTarget<>())
                                .whenStarting(pathfinderMob -> playCelebrateSound(this.getOnPos(), this.getBlockStateOn())),
                        new StartHunting<>()
                                .startCondition(livingEntity -> canHunt()),
                        new SetPlayerLookTarget<>()
                                .predicate(player -> {
                                    for (ItemStack itemstack : player.getHandSlots()) {
                                        Item item = itemstack.getItem();
                                        player.getItemInHand(InteractionHand.MAIN_HAND);
                                        player.getItemInHand(InteractionHand.OFF_HAND);
                                        if (itemstack.is(ModTags.Items.PIGLIN_WANTED_ITEMS)) {
                                            return true;
                                        }
                                    }
                                    return false;
                                })
                                .whenStarting(pathfinderMob -> playJealousSound(this.getOnPos(), this.getBlockStateOn()))
                ),
                new OneRandomBehaviour<>(
                        new SetRandomLookTarget<>(),
                        new SetRandomWalkTarget<>()
                                .speedModifier(SPEED_IDLE).stopIf(PiglinMerchantEntity::isHoldingItemInOffHand),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))
                ).startCondition(pathfinderMob -> canWalk())
        );
    }
    @Override
    public BrainActivityGroup<PiglinMerchantEntity> getFightTasks() {
        //noinspection unchecked
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod(COMBAT_SPEED)
                        .startCondition(entity -> isAggressive()),
                new FirstApplicableBehaviour<>(
                        new AnimatableMeleeAttack<>(MELEE_ATTACK_COOLDOWN)
                                .whenStarting(entity -> setAggressive(true))
                                .whenStarting(pathfinderMob -> playAngerSound(this.getOnPos(), this.getBlockStateOn()))
                                .whenStopping(entity -> setAggressive(false)),
                        new StayWithinDistanceOfAttackTarget<>().minDistance(0.5f).maxDistance(5f)
                )
        );
    }
    @Override
    public void handleAdditionalBrainSetup(SmartBrain<PiglinMerchantEntity> brain) {
        int i = TIME_BETWEEN_HUNTS.sample(RandomSource.create());
        BrainUtils.setForgettableMemory(brain, MemoryModuleType.HUNTED_RECENTLY, true, i);
        SmartBrainOwner.super.handleAdditionalBrainSetup(brain);
    }

    // synced data management
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CANNOT_HUNT, false);
        this.entityData.define(DATA_IS_DANCING, false);
        this.entityData.define(DATA_WILLING_TO_BARTER, true);
        this.entityData.define(DATA_HOLDING_ITEM, false);
        this.entityData.define(DATA_CAN_WALK, true);
        this.entityData.define(DATA_IS_EATING, false);
        this.entityData.define(DATA_EATING_TIME, 0);
    }
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
    }

    // hunting  switch
    public void setCannotHunt(boolean pCannotHunt) {
        this.entityData.set(DATA_CANNOT_HUNT, pCannotHunt);
    }
    public boolean canHunt() {
        return !this.entityData.get(DATA_CANNOT_HUNT);
    }
    // dance switch
    public void setDancing(boolean pDancing) {
        this.entityData.set(DATA_IS_DANCING, pDancing);
    }
    public boolean isDancing() {
        return this.entityData.get(DATA_IS_DANCING);
    }
    // barter  switch
    public void setWillingToBarter(boolean pWillingToBarter) {
        this.entityData.set(DATA_WILLING_TO_BARTER, pWillingToBarter);
    }
    public boolean isWillingToBarter() {
        return !this.entityData.get(DATA_WILLING_TO_BARTER);
    }
    // holding  switch
    public void setHoldingItem(boolean pHoldingItem) {
        this.entityData.set(DATA_HOLDING_ITEM, pHoldingItem);
    }
    public boolean isHoldingItem() {
        return this.entityData.get(DATA_HOLDING_ITEM);
    }
    // walk  switch
    public void setCanWalk(boolean pCanWalk) {
        this.entityData.set(DATA_CAN_WALK, pCanWalk);
    }
    public boolean canWalk() {
        return this.entityData.get(DATA_CAN_WALK);
    }
    // eating  switches
    public void setIsEating(boolean pIsEating) {
        this.entityData.set(DATA_IS_EATING, pIsEating);
    }
    public boolean isEating() {
        return this.entityData.get(DATA_IS_EATING);
    }
    public void setEatingTime(int pEatingTime) {
        this.entityData.set(DATA_EATING_TIME, pEatingTime);
    }
    public int getEatingTime() {
        return this.entityData.get(DATA_EATING_TIME);
    }

    // inventory management
    @Override
    @VisibleForDebug
    public SimpleContainer getInventory() {
        return this.inventory;
    }
    public SlotAccess getSlot(int pSlot) {
        int i = pSlot - 300;
        return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(pSlot);
    }
    public int getContainerSize() {
        return this.inventorySize;
    }
    public void addToInventory(ItemStack stack) {
        this.inventory.addItem(stack);
    }
    public boolean canAddToInventory(ItemStack stack) {
        return this.inventory.canAddItem(stack);
    }
    private static void putInInventory(@NotNull PiglinMerchantEntity piglinMerchant, ItemStack stack) {
        if (piglinMerchant.canAddToInventory(stack)) {
            piglinMerchant.addToInventory(stack);
        }
        else {
            int i;
            for (i = 0; i < piglinMerchant.getInventory().getContainerSize(); ++i) {
                ItemStack stack1 = piglinMerchant.getInventory().getItem(i);
                if (!stack1.isEmpty()) {
                    int j = stack1.getCount();

                    for (int k = j; k > stack1.getMaxStackSize(); --k) {
                        piglinMerchant.getInventory().removeItem(i, 1);
                        throwItemsTowardRandomPos(piglinMerchant, Collections.singletonList(stack));
                    }
                }
            }
        }
    }
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.inventory.fromTag(pCompound.getList("Inventory", 10));
    }
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("Inventory", this.inventory.createTag());
    }
    public boolean wantsToPickUp(@NotNull ItemStack stack) {
        Item item = stack.getItem();
        return ForgeEventFactory.getMobGriefingEvent(this.level, this) && this.canPickUpLoot() && wantsToPickUp(stack, this);
    }
    public static boolean wantsToPickUp(@NotNull ItemStack stack, @NotNull PiglinMerchantEntity piglinMerchant) {
        if (stack.is(ItemTags.PIGLIN_REPELLENTS)) {
            return false;
        }
        else if (isAdmiringDisabled(piglinMerchant)
                && piglinMerchant.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        }
        else if (isWantedItem(stack) && piglinMerchant.isWillingToBarter()) {
            return piglinMerchant.isNotHoldingWantedItemInOffHand();
        }
        else {
            boolean flag = piglinMerchant.canAddToInventory(stack);
            if (stack.is(Items.GOLD_NUGGET)) {
                return flag;
            }
            else if (EatFood.isFood(stack)) {
                return EatFood.hasNotEatenRecently(piglinMerchant) && flag;
            }
            else if (!isLovedItem(stack)) {
                return piglinMerchant.canReplaceCurrentItem(stack, stack);
            }
            else {
                return piglinMerchant.isNotHoldingWantedItemInOffHand() && flag;
            }
        }
    }

    /** "item shuffle" as the entity examines what it has in hand and decides what to do with it **/
    protected boolean canReplaceCurrentItem(ItemStack pCandidate) {
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(pCandidate);
        ItemStack itemstack = this.getItemBySlot(equipmentslot);
        return this.canReplaceCurrentItem(pCandidate, itemstack);
    }
    protected boolean canReplaceCurrentItem(@NotNull ItemStack pCandidate, @NotNull ItemStack pExisting) {
        if (EnchantmentHelper.hasBindingCurse(pExisting)) {
            return false;
        } else {
            boolean flag = isLovedItem(pCandidate) || pCandidate.is(Items.GOLDEN_AXE);
            boolean flag1 = isLovedItem(pExisting) || pExisting.is(Items.GOLDEN_AXE);
            if (flag && !flag1) {
                return true;
            } else if (!flag && flag1) {
                return false;
            } else {
                return (pCandidate.is(Items.GOLDEN_AXE) || !pExisting.is(Items.GOLDEN_AXE)) &&
                        super.canReplaceCurrentItem(pCandidate, pExisting);
            }
        }
    }

    /** this is where the "barter recipe" check begins **/
    protected void pickUpItem(@NotNull ItemEntity itemEntity) {
        this.onItemPickup(itemEntity);
        setHoldingItem(true);
        stopWalking(this);
        ItemStack stack = this.getItemInHand(InteractionHand.OFF_HAND);
        this.setItemSlot(EquipmentSlot.OFFHAND, stack);
        this.getItemInHand(InteractionHand.OFF_HAND);
        this.setItemInHand(InteractionHand.OFF_HAND, stack);

        if (itemEntity.getItem().is(Items.GOLD_NUGGET)) {
            this.take(itemEntity, itemEntity.getItem().getCount());
            stack = itemEntity.getItem();
            itemEntity.discard();
        }
        else {
            this.take(itemEntity, 1);
            stack = removeOneItemFromItemEntity(itemEntity);
        }
        if (isLovedItem(stack)) {
            BrainUtils.clearMemory(this, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            holdInOffhand(this, itemEntity.getItem());
            admireItem(this);
        }
        else if (EatFood.isFood(stack) && EatFood.hasNotEatenRecently(this)) {
            holdInOffhand(this, itemEntity.getItem());
            EatFood.eat( this);
        }
        else if (EatFood.isFood(stack) && !EatFood.hasNotEatenRecently(this)) {
            itemEntity.discard();
        }
        else {
            boolean flag = this.equipItemIfPossible(stack).isEmpty();
            if (!flag) {
                putInInventory(this, stack);
            }
        }
    }
    private static @NotNull ItemStack removeOneItemFromItemEntity(@NotNull ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        ItemStack stack1 = stack.split(1);
        if (stack.isEmpty()) {
            itemEntity.discard();
        }
        else {
            itemEntity.setItem(stack);
        }
        return stack1;
    }

    // TODO setup custom trades
    /** this may also be where right-clicking empty-handed opens up a trade or bartering window?
     * creating a bartering GUI might bypass a lot of issues actually -
     * at first it seemed weird to me that this takes two separate methods to make this exchange happen
     * but when I got to looking at them together in the same file next to each other, what I see happening
     * is a couple of checks being made, one primary check for server side and the other being made
     * for whatever the entity is holding -
     * to compare this with something like a furnace, one method is basic happening before opening the GUI
     * and the other happens after it's opened (I think)
     * in any case, whenever I try to tinker with it, it becomes a pain so for now I'm going to leave it as they are
     * with this note until I decide if it needs to stay as is or be changed **/
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult interactionresult = super.mobInteract(player, hand);
        if (interactionresult.consumesAction()) {
            return interactionresult;
        }
        else if (!this.level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (canAdmire(this, itemStack)) {
                ItemStack itemStack1 = itemStack.split(1);
                holdInOffhand(this, itemStack1);
                admireItem(this);
                stopWalking(this);
                setHoldingItem(true);
                return InteractionResult.CONSUME;
            }
            else {
                return InteractionResult.PASS;
            }
        }
        else {
            //checks if the item the player is holding while clicking on this entity is a loved item and if the entity is able to admire it
            //may need to come back to edit this for barter currency specifically?
            boolean flag = canAdmire(this, player.getItemInHand(hand)) &&
                    !isWantedItem(this.getOffhandItem()) && !BrainUtils.hasMemory(this, MemoryModuleType.DANCING) &&
                    isWillingToBarter();
            return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
    }

    // TODO consider maybe bringing back the crossbow if possible
    protected boolean isHoldingMeleeWeapon() {
        return this.getMainHandItem().getItem() instanceof TieredItem;
    }
    private static void stopWalking(PathfinderMob pathfinderMob) {
        if (isHoldingItemInOffHand(pathfinderMob)) {
            BrainUtils.clearMemory(pathfinderMob, MemoryModuleType.WALK_TARGET);
            pathfinderMob.getNavigation().stop();
        }
    }
    protected void holdInMainHand(ItemStack pStack) {
        this.setItemSlotAndDropWhenKilled(EquipmentSlot.MAINHAND, pStack);
    }
    public static void holdInOffhand(@NotNull PiglinMerchantEntity piglinMerchant, @NotNull ItemStack stack) {
        piglinMerchant.spawnAtLocation(piglinMerchant.getItemInHand(InteractionHand.OFF_HAND));
        if (stack.is(ModTags.Items.PIGLIN_WANTED_ITEMS)) {
            piglinMerchant.setItemInHand(InteractionHand.OFF_HAND, stack);
            piglinMerchant.setItemSlot(EquipmentSlot.OFFHAND, stack);
            piglinMerchant.setGuaranteedDrop(EquipmentSlot.OFFHAND);
            piglinMerchant.swing(InteractionHand.OFF_HAND);
        }
        else {
            piglinMerchant.setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, stack);
            piglinMerchant.swing(InteractionHand.OFF_HAND);
        }
    }
    private boolean isNotHoldingWantedItemInOffHand() {
        return this.getOffhandItem().isEmpty() || !this.getOffhandItem().is(ModTags.Items.PIGLIN_WANTED_ITEMS);
    }
    private static boolean isHoldingItemInOffHand(@NotNull LivingEntity livingEntity) {
        return !livingEntity.getOffhandItem().isEmpty();
    }
    public static void isBarterCurrency() {
        ItemStack stack = ItemStack.EMPTY;
        stack.is(ModTags.Items.PIGLIN_BARTER_ITEMS);
    }
    public static boolean isLovedItem(@NotNull ItemStack stack) {
        return stack.is(ItemTags.PIGLIN_LOVED);
    }
    public static boolean isWantedItem(@NotNull ItemStack stack) {
        return stack.is(ModTags.Items.PIGLIN_WANTED_ITEMS);
    }
    public static boolean canAdmire(PiglinMerchantEntity piglinMerchant, ItemStack stack) {
        return !isAdmiringDisabled(piglinMerchant) && !isAdmiringItem(piglinMerchant)
                && stack.is(ModTags.Items.PIGLIN_WANTED_ITEMS);
    }
    public static void admireItem(@NotNull PiglinMerchantEntity piglinMerchant) {
        BrainUtils.setForgettableMemory(piglinMerchant, MemoryModuleType.ADMIRING_ITEM, true, ADMIRE_DURATION);
    }
    public static boolean isAdmiringItem(@NotNull PiglinMerchantEntity piglinMerchant) {
        return BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.ADMIRING_ITEM);
    }
    public static boolean isAdmiringDisabled(@NotNull PiglinMerchantEntity piglinMerchant) {
        return BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.ADMIRING_DISABLED);
    }
    public static boolean isPlayerHoldingLovedItem(@NotNull Player player) {
        return player.getType() == EntityType.PLAYER && player.isHolding(PiglinMerchantEntity::isLovedItem);
    }
    public static boolean seesPlayerHoldingLovedItem(PiglinMerchantEntity piglinMerchant) {
        return BrainUtils.hasMemory(piglinMerchant, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    /** this is where the bartering item check event actually happens **/
    public static void stopHoldingOffHandItem(@NotNull PiglinMerchantEntity piglinMerchant, boolean pShouldBarter) {
        ItemStack offHandItem = piglinMerchant.getItemInHand(InteractionHand.OFF_HAND);
        piglinMerchant.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        boolean flag = offHandItem.is(ModTags.Items.PIGLIN_BARTER_ITEMS);
        if (pShouldBarter && flag) {
            //maybe dance?
            wantsToDance(piglinMerchant);
            throwItems(piglinMerchant, getBarterResponseItems(piglinMerchant));
        }
        else if (!flag) {
            boolean flag1 = piglinMerchant.equipItemIfPossible(offHandItem).isEmpty();
            if (!flag1) {
                putInInventory(piglinMerchant, offHandItem);
            }
        }
        else {
            boolean flag2 = piglinMerchant.equipItemIfPossible(offHandItem).isEmpty();
            if (!flag2) {
                ItemStack mainHandItem = piglinMerchant.getMainHandItem();
                if (isLovedItem(mainHandItem)) {
                    putInInventory(piglinMerchant, mainHandItem);
                }
                else {
                    throwItems(piglinMerchant, Collections.singletonList(mainHandItem));
                }

                piglinMerchant.holdInMainHand(offHandItem);
            }
        }
    }
    // TODO setup a custom bartering loot tier  system
    /** if I can get the custom currency working then maybe I can then get custom loot tables running too
     * it's very likely that I can get this running with the sensor **/
    private static @NotNull List<ItemStack> getBarterResponseItems(@NotNull PiglinMerchantEntity piglinMerchant) {
        LootTable lootTable = Objects.requireNonNull(piglinMerchant.level.getServer()).getLootTables().get(BuiltInLootTables.PIGLIN_BARTERING);
        return lootTable.getRandomItems((
                new LootContext.Builder((ServerLevel)piglinMerchant.level))
                .withParameter(LootContextParams.THIS_ENTITY, piglinMerchant)
                .withRandom(piglinMerchant.level.random).create(LootContextParamSets.PIGLIN_BARTER));
    }
    // items thrown in response to item held
    private static void throwItems(@NotNull PiglinMerchantEntity piglinMerchant, List<ItemStack> stacks) {
        Optional<Player> optional = piglinMerchant.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            throwItemsTowardPlayer(piglinMerchant, optional.get(), stacks);
        }
        else {
            throwItemsTowardRandomPos(piglinMerchant, stacks);
        }
    }
    private static void throwItemsTowardRandomPos(@NotNull PiglinMerchantEntity piglinMerchant, List<ItemStack> stacks) {
        throwItemsTowardPos(piglinMerchant, stacks, getRandomNearbyPos(piglinMerchant));
    }
    private static void throwItemsTowardPlayer(@NotNull PiglinMerchantEntity piglinMerchant, @NotNull Player pPlayer, List<ItemStack> stacks) {
        throwItemsTowardPos(piglinMerchant, stacks, pPlayer.position());
    }
    private static void throwItemsTowardPos(@NotNull PiglinMerchantEntity piglinMerchant, @NotNull List<ItemStack> stacks, Vec3 pPos) {
        if (!stacks.isEmpty()) {
            piglinMerchant.swing(InteractionHand.OFF_HAND);
            for(ItemStack itemstack : stacks) {
                BehaviorUtils.throwItem(piglinMerchant, itemstack, pPos.add(0.0D, 1.0D, 0.0D));
            }
        }
    }
    private static @NotNull Vec3 getRandomNearbyPos(PiglinMerchantEntity piglinMerchant) {
        Vec3 vec3 = LandRandomPos.getPos(piglinMerchant, 4, 2);
        return vec3 == null ? piglinMerchant.position() : vec3;
    }
    public static void wantsToDance(@NotNull PiglinMerchantEntity piglinMerchant) {
        float DanceChance;
        DanceChance = RandomSource.create(piglinMerchant.level.getGameTime()).nextFloat();

        if (DanceChance < PROBABILITY_OF_CELEBRATION_DANCE) {
            piglinMerchant.setDancing(true);
        }
    }
    public static boolean isIdle(@NotNull PiglinMerchantEntity piglinMerchant) {
        return piglinMerchant.getBrain().isActive(Activity.IDLE);
    }
    public boolean hurt(@NotNull DamageSource dmgSrc, float dmgAmt) {
        boolean flag = super.hurt(dmgSrc, dmgAmt);
        if (this.level.isClientSide) {
            return false;
        }
        else {
            if (flag && dmgSrc.getEntity() instanceof LivingEntity) {
                wasHurtBy(this, (LivingEntity)dmgSrc.getEntity());
            }
            return flag;
        }
    }
    public void wasHurtBy(PiglinMerchantEntity todePiglinMerchant, LivingEntity hurtBy) {
        if (!(hurtBy instanceof PiglinMerchantEntity) && !(hurtBy instanceof AbstractPiglin) && !(hurtBy instanceof AbstractVillager)) {
            if (isHoldingItemInOffHand(todePiglinMerchant)) {
                stopHoldingOffHandItem(todePiglinMerchant, false);
            }
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.CELEBRATE_LOCATION);
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.DANCING);
            BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.ADMIRING_ITEM);
            if (hurtBy instanceof Player) {
                BrainUtils.setForgettableMemory(todePiglinMerchant, MemoryModuleType.ADMIRING_DISABLED, true, HIT_BY_PLAYER_MEMORY_TIMEOUT);
            }
            PiglinMerchantSpecificSensor.getAvoidTarget(todePiglinMerchant).ifPresent((livingEntity) -> {
                if (livingEntity.getType() != hurtBy.getType()) {
                    BrainUtils.clearMemory(todePiglinMerchant, MemoryModuleType.AVOID_TARGET);
                }
            });
            if (hurtBy.getType() == EntityType.HOGLIN && HoglinHuntSensor.hoglinsOutnumberPiglins(todePiglinMerchant)) {
                HoglinHuntSensor.setAvoidTargetAndDontHuntForAWhile(todePiglinMerchant, hurtBy);
                HoglinHuntSensor.broadcastRetreat(todePiglinMerchant, hurtBy);
            }
            else {
                SetAngerTarget.maybeRetaliate(todePiglinMerchant, hurtBy);
            }
        }
    }


    // TODO setup custom sounds for the entity with subtitles
    //  - maybe even remix the piglin and piglin brute sounds to come up with something a bit more unique?
    /** SOUND EVENTS **/
    protected SoundEvent getAmbientSound() {
        return this.level.isClientSide ? null : SoundEvents.PIGLIN_AMBIENT;
    }
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.PIGLIN_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIGLIN_DEATH;
    }
    protected void playStepSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_STEP, 0.15F, 1.0F);
    }
    protected void playAngerSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_ANGRY, 0.15F, 1.0F);
    }
    protected void playRetreatSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_RETREAT, 0.15F, 1.0F);
    }
    protected void playAdmireSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_ADMIRING_ITEM, 0.15F, 1.0F);
    }
    protected void playCelebrateSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_CELEBRATE, 0.15F, 1.0F);
    }
    protected void playJealousSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.PIGLIN_JEALOUS, 0.15F, 1.0F);
    }
    protected void playEatSound(@NotNull BlockPos pPos, @NotNull BlockState pBlock) {
        this.playSound(SoundEvents.GENERIC_EAT, 0.15F, 1.0F);
    }
    @Override
    public void registerControllers(@NotNull AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "defaultController", 0, this::defaultPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "danceController", 0, this::dancePredicate));
        controllerRegistrar.add(new AnimationController<>(this, "acceptController", 0, this::acceptPredicate)
                .setParticleKeyframeHandler(state -> {
                    // Use helper method to avoid client-code in common class
                    Player player = ClientUtils.getClientPlayer();

                    if (player != null) {
                        for (int i = 0; i < 7; ++i) {
                            double d0 = this.random.nextGaussian() * 0.02D;
                            double d1 = this.random.nextGaussian() * 0.02D;
                            double d2 = this.random.nextGaussian() * 0.02D;
                            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
                        }
                    }
                }));
        controllerRegistrar.add(new AnimationController<>(this, "rejectController", 0, this::rejectPredicate)
                .setParticleKeyframeHandler(state -> {
                    // Use helper method to avoid client-code in common class
                    Player player = ClientUtils.getClientPlayer();

                    if (player != null) {
                        for (int i = 0; i < 7; ++i) {
                            double d0 = this.random.nextGaussian() * 0.02D;
                            double d1 = this.random.nextGaussian() * 0.02D;
                            double d2 = this.random.nextGaussian() * 0.02D;
                            this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
                        }
                    }
                }));
        controllerRegistrar.add(new AnimationController<>(this, "eatController", 0, this::eatPredicate)
                .setParticleKeyframeHandler(state -> {
                    // Use helper method to avoid client-code in common class
                    Player player = ClientUtils.getClientPlayer();

                    if (player != null) {
                        ItemStack stack = this.getOffhandItem();
                        for (int i = 0; i < 7; ++i) {
                            Vec3 vec3 = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
                            vec3 = vec3.xRot(-this.getXRot() * ((float) Math.PI / 180F));
                            vec3 = vec3.yRot(-this.getYRot() * ((float) Math.PI / 180F));
                            double d0 = (double) (-this.random.nextFloat()) * 0.6D - 0.3D;
                            Vec3 vec31 = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double) this.random.nextFloat() - 0.5D) * 0.4D);
                            vec31 = vec31.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
                            vec31 = vec31.add(this.getX(), this.getEyeY() - 0.2D, this.getZ());
                            this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
                        }
                    }
                })
                .setSoundKeyframeHandler(state -> {
                    // Use helper method to avoid client-code in common class
                    Player player = ClientUtils.getClientPlayer();

                    if (player != null) {
                        player.playSound(SoundEvents.GENERIC_EAT, 1, 1);
                    }
                })
        );
        controllerRegistrar.add(new AnimationController<>(this, "meleeController", 0, this::meleePredicate));
    }

    private PlayState defaultPredicate(@NotNull AnimationState<GeoAnimatable> animationState) {
        if (animationState.isMoving()) {
            if (this.getOffhandItem().isEmpty() && !this.isAggressive()) {
                animationState.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            }
            else {
                animationState.getController().setAnimation(RawAnimation.begin().thenLoop("walk_legs_only"));
            }
        }
        else {
            animationState.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.CONTINUE;
    }
    private PlayState dancePredicate(AnimationState<GeoAnimatable> animationState) {
        if (this.swinging && getArmPose() == PiglinMerchantArmPose.DANCING) {
            animationState.getController().setAnimation(RawAnimation.begin().thenLoop("dance"));
        }
        else if (getArmPose() == PiglinMerchantArmPose.DANCING && animationState.getController().hasAnimationFinished()) {
            animationState.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    private PlayState acceptPredicate(AnimationState<GeoAnimatable> animationState) {
        // it's the opposite hand moving here since the main hand goes to the handedness of the entity
        if (!this.isEating() && this.swinging && getArmPose() == PiglinMerchantArmPose.ACCEPT_ITEM) {
            if (isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("accept_right")
                        .thenWait(60)
                        .thenPlayXTimes("nod_yes", 4)
                        .thenPlay("default"));
            }
            else if (!isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("accept_left")
                        .thenWait(60)
                        .thenPlayXTimes("nod_yes", 4)
                        .thenPlay("default"));
            }
        }
        else if (getArmPose() == PiglinMerchantArmPose.ACCEPT_ITEM && animationState.getController().hasAnimationFinished()) {
            animationState.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    private PlayState rejectPredicate(AnimationState<GeoAnimatable> animationState) {
        // it's the opposite hand moving here since the main hand goes to the handedness of the entity
        if (!this.isEating() && this.swinging && getArmPose() == PiglinMerchantArmPose.REJECT_ITEM) {
            if (isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("reject_right")
                        .thenPlayXTimes("nod_no", 4));
            }
            else if (!isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("reject_left")
                        .thenPlayXTimes("nod_no", 4));
            }
        }
        else if (getArmPose() == PiglinMerchantArmPose.REJECT_ITEM && animationState.getController().hasAnimationFinished()) {
            animationState.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    private PlayState eatPredicate(AnimationState<GeoAnimatable> animationState) {
        // it's the opposite hand moving here since the main hand goes to the handedness of the entity
        if (this.swinging && getArmPose() == PiglinMerchantArmPose.EAT) {
            if (isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("eat_right"));
            }
            else if (!isLeftHanded()) {
                animationState.getController().setAnimation(RawAnimation.begin()
                        .thenPlay("eat_left"));
            }
        }
        else if (getArmPose() == PiglinMerchantArmPose.EAT && animationState.getController().hasAnimationFinished()) {
            animationState.getController().forceAnimationReset();
        }
        return PlayState.CONTINUE;
    }
    private PlayState meleePredicate(AnimationState<GeoAnimatable> animationState) {
        if (getArmPose() == PiglinMerchantArmPose.ATTACKING_WITH_MELEE_WEAPON) {
            if (this.isAggressive() && this.swinging) {
                if (isLeftHanded()) {
                    animationState.getController().setAnimation(RawAnimation.begin().thenPlay("melee_left"));
                }
                else if (!isLeftHanded()) {
                    animationState.getController().setAnimation(RawAnimation.begin().thenPlay("melee_right"));
                }
            }
        }
        return PlayState.CONTINUE;
    }

    public PiglinMerchantArmPose getArmPose() {
        ItemStack stack = this.getItemInHand(InteractionHand.OFF_HAND);
        if (this.isDancing()) {
            return PiglinMerchantArmPose.DANCING;
        }
        else if (isHoldingItem() && stack.is(ItemTags.PIGLIN_FOOD)) {
            return PiglinMerchantArmPose.EAT;
        }
        else if (isHoldingItem() && stack.is(ModTags.Items.PIGLIN_BARTER_ITEMS)) {
            return PiglinMerchantArmPose.ACCEPT_ITEM;
        }
        else if (isHoldingItem() && !stack.is(ModTags.Items.PIGLIN_BARTER_ITEMS) && !stack.is(ItemTags.PIGLIN_FOOD)) {
            return PiglinMerchantArmPose.REJECT_ITEM;
        }
        else if (this.isAggressive() && this.isHoldingMeleeWeapon()) {
            return PiglinMerchantArmPose.ATTACKING_WITH_MELEE_WEAPON;
        }
        else {
            return PiglinMerchantArmPose.DEFAULT;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    protected void customServerAiStep() {
        if (!BrainUtils.hasMemory(this.brain, MemoryModuleType.CELEBRATE_LOCATION)) {
            BrainUtils.setMemory(this.brain, MemoryModuleType.DANCING, false);
            setDancing(false);
        }
        else {
            BrainUtils.setMemory(this.brain, MemoryModuleType.DANCING, true);
            setDancing(true);
        }

        if (this.entityData.get(DATA_CANNOT_HUNT) && !BrainUtils.hasMemory(this.brain, MemoryModuleType.HUNTED_RECENTLY)) {
            BrainUtils.setForgettableMemory(this,
                    MemoryModuleType.HUNTED_RECENTLY, true, TIME_BETWEEN_HUNTS.sample(this.level.random));
        }
        else {
            setCannotHunt(true);
            BrainUtils.clearMemory(this.brain, MemoryModuleType.HUNTED_RECENTLY);
        }

        if (isEating()) {
            ItemStack stack = this.getOffhandItem();
            int usingTime = this.getOffhandItem().getUseDuration();
            setEatingTime(usingTime);
            int tickCount = getEatingTime();

            while ((getEatingTime() > 0) && (tickCount > 0)) {
                tickCount--;
                setEatingTime(tickCount--);
            }
            if ((getEatingTime() <= 0) && (tickCount <= 0)) {
                getEatingTime();
                setIsEating(false);
                stack.finishUsingItem(this.level, this);
            }
        }

        setWillingToBarter(!BrainUtils.hasMemory(this, MemoryModuleType.DANCING));

        setHoldingItem(isHoldingItemInOffHand(this));

        setCanWalk(!isHoldingItem());

        updateSwingTime();
        // must be here to tick the brain server side //
        tickBrain(this);
    }
}