export interface Versions {
    id: number;
    versionGroup : VersionGroup;
    identifier: string;
}
export interface VersionGroup{
    id: number;
    identifier: string;
    generationId:Generation;
}
export interface Generation {
    id:number;
    identifier:string;
    region:Region;
}
export interface Region {
    id: number;
    identifier: string;
}
export interface Route {
    id: number;
    identifier: string;
    region_id: number;
}
export interface Areas {
    id : number
    location_id : number
    game_index : number
    identifier : string
}

export interface EncounterMethodId {
    id : number
    identifier : string
}

export interface EncounterSlots{
    id : number
    rarity : number
    slot: number
    versionGroup: VersionGroup
    encounterMethodId : EncounterMethodId
}
export interface Encounter {
    id : number
    minLevel : number
    maxLevel : number
    locationArea: Areas
    encounterSlots : EncounterSlots
    identifier : string
    pokemon : Pokemon
}
export interface Pokemon {
    id: number;
    baseXp: number;
    height : number;
    weight : number;
    identifier: string;
    species : PokemonSpecies;
    collected: boolean;
    hasEvolution: boolean;
}
export interface PoedexPokemon{
    id: number;
    isCollected: number;
    species: PokemonSpecies;
    canBeEvolvedTo: PoedexPokemon[];
    canBeBreadTo: [];

}
export interface toCollect {
    pokemon: Pokemon
    encounters: string[]
}

export interface PokemonSpecies{
    id: number
    identifier : number
    generationId : number
    evolvesFomSpecies : PokemonSpecies;
    evolution_chain : EvolutionChain
    color_id : number
    shape_id : number
    habitat_id : number
    gender_rate : number
    capture_rate : number
    base_happiness : number
    is_baby : Boolean
    hatch_counter: number
    has_gender_differences: Boolean
    growth_rate_id: number
    forms_switchable: Boolean
    is_legendary: Boolean
    is_mythical: Boolean
    order: number
}
export interface EvolutionChain {
    id: number;
    baby_trigger_item: Item;
}
export interface Item{
    id:number
    identifier: string
    category_id: number
    cost: number
    fling_power: number
    fling_effect_id: number
}