import React, { useState, useEffect} from 'react'
import {Autocomplete, Box, TextField, Typography} from "@mui/material";
import axios from "axios";
import AddIcon from '@mui/icons-material/Add';
import {Versions, Encounter,EncounterMethodId,Pokemon,Areas,PoedexPokemon,Region,Route} from "../Models.ts";
import EvolutionsPopup from "./EvolutionsPopup.tsx";
import "./Navcomponent.css"
import ToCollect from "./ToCollect.tsx";
import Pokedex from "./Pokedex.tsx";
import LinearProgress, { LinearProgressProps } from '@mui/material/LinearProgress';
import CustomAdd from "./CustomAdd.tsx";

function LinearProgressWithLabel(props: LinearProgressProps & { value: number, maxVal:number }) {
    return (
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Box sx={{ width: '100%', position: 'relative', height: '2rem' }}>
                {/* Progress Bar */}
                <LinearProgress
                    sx={{ height: '2rem' }}
                    variant="determinate"
                    {...props}
                />
                {/* Overlapping Text */}
                <Typography
                    variant="body2"
                    sx={{
                        color: 'text.secondary',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                    }}
                >
                    { props.value + "/"+props.maxVal}
                </Typography>
            </Box>
        </Box>
    );
}



function NavComponent() {



    let [selectableVersions,setSelectableVersions] = useState<Versions[]>([]);
    let [selectableRoute,setSelectableRoute] = useState<Route[]>([]);

    let [selectedVersion,setSelectedVersion] = useState<Versions>();
    let [selectedRegion,setSelectedRegion] = useState<Region>();
    let [selectedRoute,setSelectedRoute] = useState<Route>();
    let [selectedAreas,setSelectedAreas] = useState<Areas[]>();
    let [selectedCollected,setSelectedCollected] = useState<PoedexPokemon[]>([]);
    let [selectedPokemons, setSelectedPokemons] = useState<Map<EncounterMethodId,Map<Pokemon,Encounter[]>>>( new Map());
    let [updateSelectedPokemons, setUpdateSelectedPokemons] = useState<number>(0);
    let [currentProgress, setCurrentProgress] = useState<number>(0);
    let [maxProgress, setMaxProgress] = useState<number>(0);
    let [update, setUpdate] = useState<number>(0);
   // let [selectedPokemons, setSelectedPokemons] = useState<Map<EncounterMethodId,Map<Pokemon,Encounter[]>>>( new Map());
    const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;



    useEffect(() => {

        //GRAB Version
        axios.get(`${API_BASE_URL}/version`).then((resp)=>{
            let cversions: Versions[] = resp.data as Versions[];
            setSelectableVersions(cversions);

        })

    }, []);
    useEffect(() => {
        if(selectedRegion!= undefined) {
            axios.get(`${API_BASE_URL}/routes?route_id=` + String(selectedRegion.id)).then((resp) => {
                setSelectableRoute(resp.data as Route[]);

            })
        }
    }, [selectedRegion]);

    useEffect(() => {
        if(selectedRoute!= null) {
            axios.get(`${API_BASE_URL}/area?location_id=` + String(selectedRoute.id)).then((resp) => {
                setSelectedAreas(resp.data as Areas[]);
            })
        }
    }, [selectedRoute]);

    useEffect(()=>{
        if(selectedVersion != null){
           setSelectedRegion(selectedVersion.versionGroup.generationId.region);
           axios.get(`${API_BASE_URL}/pokedex/currentcompeate?version=` + selectedVersion?.id).then((resp) => {
                setCurrentProgress(resp.data[0] as number);
                setMaxProgress(resp.data[1] as number);
                console.log(resp.data);
           })
        }




    },[selectedVersion,update])


    useEffect(() => {

        if(selectedAreas != undefined && selectedVersion!= undefined) {
            selectedAreas.forEach((it) => {

                let adress = `${API_BASE_URL}/getPokemonsInArea?location_area_id=` + String(it.id) + "&version_id=" + selectedVersion.id
                axios.get(adress).then((resp)=>{
                    const data = resp.data;
                    const newSelectedPokemons = new Map<EncounterMethodId, Map<Pokemon, Encounter[]>>();

                    // Iterate through the keys (EncounterMethodId objects)
                    Object.entries(data).forEach(([encounterMethodStr, pokemonMap]) => {
                        const encounterMethod = JSON.parse(encounterMethodStr) as EncounterMethodId;

                        // Create a map for pokemon and their encounters
                        const pokemonMapForMethod = new Map<Pokemon, Encounter[]>();
                        // Iterate through the Pokemon entries
                        // @ts-ignore
                        Object.entries(pokemonMap).forEach(([pokemonStr, encounters]) => {
                            const pokemon = JSON.parse(pokemonStr) as Pokemon;

                            // @ts-ignore
                            const encounterList: Encounter[] = encounters.map((encounterData: any) => ({
                                id: encounterData.id,
                                minLevel: encounterData.minLevel,
                                maxLevel: encounterData.maxLevel,
                                locationArea: encounterData.locationArea,
                                encounterSlots: encounterData.encounterSlots,
                                identifier: encounterData.identifier,
                                pokemon: pokemon,
                            }));

                            pokemonMapForMethod.set(pokemon, encounterList);
                        });

                        newSelectedPokemons.set(encounterMethod, pokemonMapForMethod);
                    });

                    // Update state with the transformed data
                    setSelectedPokemons(newSelectedPokemons);
                })

            });
        }
    },[selectedAreas, selectedVersion,update])
    useEffect(() => {
        setSelectedCollected([])
        Array.from(selectedPokemons.entries()).forEach(
            ([_, pokemonMap]) => (
                Array.from(pokemonMap.entries()).forEach(([pk,_]) =>(
                     hasPokemon(pk)
                    )
                )))


    }, [selectedPokemons, updateSelectedPokemons,update]);


    const handleClick:React.FormEventHandler<HTMLFormElement> = (form ) => {
        form.preventDefault();
        
    }
    const addPokemon:React.MouseEventHandler<HTMLDivElement> = (target) => {
        let adress = `${API_BASE_URL}/pokedex?pokemon_id=`+target.currentTarget.id
        axios.put(adress).then(resp => (resp.status == 200)?(
            setUpdateSelectedPokemons(s => s+1)):console.log(resp.data))
        setUpdate(it => it+1)
    }

    const hasPokemon = (pokemon:Pokemon) => {
        let address = `${API_BASE_URL}/pokedex?pokemon_id=`+pokemon.id
        axios.get(address).then((resp) => {
            const newPokemon = resp.data as PoedexPokemon;
            setSelectedCollected((prev) => [...prev, newPokemon]);
        });

    }

    const changeTextColor = (pokemonid:Number):{} =>{
        if(selectedCollected){
            var spe = selectedCollected.find(it => it.species.id === pokemonid)
            if (spe != undefined){
                if(spe.canBeEvolvedTo.filter(it2 => it2.isCollected==0).length == 0 && spe.isCollected>0 ) return {"color":"green"}
                if (spe.isCollected>0 ) return {"color":"yellow"}
            }
        }

        return {"color":"gray"}
    }


    // @ts-ignore
    return (
        <section >
            <div className="topRow">
        <Autocomplete
            disablePortal
            options={selectableVersions}
            sx={{
                width: '100%',
                '& .MuiInputBase-input': {
                    color: 'white', // Input text color
                },
                '& .MuiInputLabel-root': {
                    color: 'white', // Label text color
                },
                '& .MuiOutlinedInput-notchedOutline': {
                    borderColor: 'white', // Border color
                },
                '& .MuiAutocomplete-popupIndicator': {
                    color: 'white', // Dropdown icon color
                },
            }}
            renderInput={(params) => <TextField {...params} label="Version" InputLabelProps={{
                style: { color: 'white' }, // Label styling
            }}/>}
            onChange={(_, value) => {
                if (value != null) {
                    setSelectedVersion(value)
                }
            }}
            getOptionLabel={(opt) => opt.identifier}
        />
           <CustomAdd setversion={()=>{setUpdate(it=> it+1)}}></CustomAdd>
        </div>
        <LinearProgressWithLabel  variant="determinate" value={currentProgress} maxVal={maxProgress}/>
        <div className="sectionmain">
        <Pokedex version={selectedVersion} update={update} setUpdate={()=>{setUpdate(it => it+1)}}></Pokedex>
            <form onSubmit={handleClick}>


                <Autocomplete
                    disablePortal
                    options={selectableRoute}
                    className="sum"
                    sx={{
                        mt: "1rem",
                        height: 100,
                        width: 400,
                        '& .MuiInputBase-input': {
                            color: 'white', // Input text color
                        },
                        '& .MuiInputLabel-root': {
                            color: 'white', // Label text color
                        },
                        '& .MuiOutlinedInput-notchedOutline': {
                            borderColor: 'white', // Border color
                        },
                        '& .MuiAutocomplete-popupIndicator': {
                            color: 'white', // Dropdown icon color
                        },
                    }}
                    renderInput={(params) => <TextField {...params} label="Route"/>}
                    onChange={(_, value) => {
                        if (value != null) setSelectedRoute(value)
                    }}
                    getOptionLabel={(opt) => opt.identifier}
                />
                <h3>{selectedRegion?.identifier}</h3>

                <div>
                    {
                        (selectedPokemons && selectedPokemons.size > 0) ?
                            Array.from(selectedPokemons.entries()).map(
                                ([encounterMethod, pokemonMap]) => (
                                    <div key={encounterMethod.id}> {/* Key based on EncounterMethodId */}
                                        <h2>Encounter Method: {encounterMethod.identifier}</h2>
                                        {Array.from(pokemonMap.entries()).map(
                                            ([pokemon, encounters]) => (
                                                <div className="pokemonGroup"
                                                     style={changeTextColor(pokemon.species.id)}
                                                     key={pokemon.species.id}> {/* Key based on Pokemon ID */}
                                                    <h3 title={
                                                        encounters.map((encounter) => {
                                                            return `Level Range: ${encounter.minLevel} - ${encounter.maxLevel}, Rarity: ${encounter.encounterSlots.rarity}`;
                                                        }).join('  \n ') // Joining the string values with a separator
                                                    }>
                                                        {pokemon.identifier}
                                                    </h3>
                                                    <EvolutionsPopup pokemonId={pokemon.species.id}
                                                                     selectedCollected={selectedCollected}
                                                                     generation={selectedVersion?.versionGroup.generationId.id}></EvolutionsPopup>
                                                    <div id={pokemon.species.id + ""} onClick={addPokemon}>
                                                        <AddIcon></AddIcon></div>
                                                </div>
                                            )
                                        )}
                                    </div>
                                )
                            ) : "NULL OR 0"
                    }
                </div>


            </form>
            <div>
        <ToCollect version={selectedVersion} update={update}></ToCollect>
        </div>
        </div>
        </section>
    )

}

export default NavComponent
