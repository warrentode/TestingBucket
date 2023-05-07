package net.warrentode.recipebooktest.datagen.recipes.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.warrentode.recipebooktest.recipe.ModRecipes;
import net.warrentode.recipebooktest.recipe.recipebook.CoinPressRecipeBookTab;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static net.warrentode.recipebooktest.RecipeBookTest.MODID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoinPressRecipeBuilder {
    private CoinPressRecipeBookTab tab;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Item result;
    private final int count;
    private final int stampingTime;
    private final float experience;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    private CoinPressRecipeBuilder(ItemLike resultIn, int count, int cookingTime, float experience) {
        this.result = resultIn.asItem();
        this.count = count;
        this.stampingTime = cookingTime;
        this.experience = experience;
        this.tab = null;
    }

    public static CoinPressRecipeBuilder coinPressRecipe(ItemLike mainResult, int count, int cookingTime, float experience) {
        return new CoinPressRecipeBuilder(mainResult, count, cookingTime, experience);
    }

    public CoinPressRecipeBuilder addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public CoinPressRecipeBuilder addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public CoinPressRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public CoinPressRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public CoinPressRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }

    public CoinPressRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    public CoinPressRecipeBuilder unlockedByItems(String criterionName, ItemLike... items) {
        return unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    public CoinPressRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        advancement.addCriterion("has_any_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    public CoinPressRecipeBuilder setRecipeBookTab(CoinPressRecipeBookTab tab) {
        this.tab = tab;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(result);
        assert location != null;
        build(consumerIn, MODID + ":coinpress/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Coin Press Recipe " + save + " should remove its 'save' argument");
        }
        else {
            build(consumerIn, new ResourceLocation(save));
        }
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        if (!advancement.getCriteria().isEmpty()) {
            advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(RequirementsStrategy.OR);
            ResourceLocation advancementId = null;
            if (result.getItemCategory() != null) {
                advancementId = new ResourceLocation(id.getNamespace(), "recipes/" + result.getItemCategory().getRecipeFolderName() + "/" + id.getPath());
            }
            consumerIn.accept(new Result(id, result, count, ingredients, stampingTime, experience, tab, advancement, advancementId));
        }
        else {
            consumerIn.accept(new Result(id, result, count, ingredients, stampingTime, experience, tab));
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final CoinPressRecipeBookTab tab;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private final int stampingTime;
        private final float experience;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int stampingTimeIn, float experienceIn, @Nullable CoinPressRecipeBookTab tabIn, @Nullable Advancement.Builder advancement, @Nullable ResourceLocation advancementId) {
            this.id = idIn;
            this.tab = tabIn;
            this.ingredients = ingredientsIn;
            this.result = resultIn;
            this.count = countIn;
            this.stampingTime = stampingTimeIn;
            this.experience = experienceIn;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int stampingTimeIn, float experienceIn, @Nullable CoinPressRecipeBookTab tabIn) {
            this(idIn, resultIn, countIn, ingredientsIn, stampingTimeIn, experienceIn, tabIn, null, null);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (tab != null) {
                json.addProperty("recipe_book_tab", tab.toString());
            }

            if (experience > 0) {
                json.addProperty("experience", experience);
            }
            json.addProperty("stampingTime", stampingTime);

            JsonArray arrayIngredients = new JsonArray();

            for (Ingredient ingredient : ingredients) {
                arrayIngredients.add(ingredient.toJson());
            }
            json.add("ingredients", arrayIngredients);

            JsonObject objectResult = new JsonObject();
            objectResult.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
            if (count > 1) {
                objectResult.addProperty("count", count);
            }
            json.add("result", objectResult);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.COINPRESS_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancement != null ? advancement.serializeToJson() : null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}