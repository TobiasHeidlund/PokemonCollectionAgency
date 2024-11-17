import { useEffect, useState } from 'react';
import { PoedexPokemon } from "../Models.ts";
import "./EvolutionPopup.css";

interface Props {
    pokemonId: number;
    selectedCollected: PoedexPokemon[];
    generation: number|undefined;
}

export default function EvolutionsPopup({ pokemonId, selectedCollected, generation }: Props) {
    // Local state for evolutions and notCollected arrays
    const [evolutions, setEvolutions] = useState<PoedexPokemon[]>([]);
    const [notCollected, setNotCollected] = useState<PoedexPokemon[]>([]);

    useEffect(() => {
        // Calculate evolutions and notCollected based on selectedCollected
        if (selectedCollected && generation) {
            var evolutionsList = selectedCollected
                .filter(value => value.species.id === pokemonId) // Find pokemon by id
                .flatMap(value => value.canBeEvolvedTo); // Get evolutions
            console.log(evolutionsList)
            setEvolutions(evolutionsList);

            var notCollectedList = evolutionsList.filter(evolution =>
                evolution.species.generationId <= generation && evolution.isCollected == 0
            );

            setNotCollected(notCollectedList);
        }
    }, [selectedCollected, pokemonId, generation]);


    const a = function(ev:any) {
        var item = document.getElementById("pokeid" + pokemonId)
        if (item != null) {
            var offsets = item.getBoundingClientRect();
            var top = offsets.top;
            var left = offsets.left;

            item.style.transform = 'translateY(' + (ev.clientY-top-40) + 'px)';
            item.style.transform += 'translateX(' + (ev.clientX-left) + 'px)';
        }
    }

    // Handle mouse enter event to show the element
    const mouseEnter = () => {
        const element = document.getElementById("pokeid" + pokemonId) as HTMLDivElement;
        if (element) {
            document.addEventListener('mousemove',a,false );
            element.classList.add("show");
            element.classList.remove("hide");

        }
    };

    // Handle mouse leave event to hide the element
    const onMouseLeave = () => {
        const element = document.getElementById("pokeid" + pokemonId) as HTMLDivElement;
        if (element) {
            element.classList.remove("show");
            element.classList.add("hide");
            document.removeEventListener('mousemove', a);
        }
    };

    return (
        <div className="pokemonEvolutionPopup">
            <div className="hide" id={"pokeid" + pokemonId}>
                <h3>Obtainable</h3>
                {notCollected.map(it => (
                    <div key={it.id}>{it.species.identifier}</div>
                ))}
                <h3>To low Gen</h3>
                {(generation!=undefined)?
                    evolutions.filter(evolution =>
                    evolution.species.generationId > generation && evolution.isCollected == 0
                ).map(it => (<div key={it.species.id}>{it.species.identifier}</div>)):""}
                <h3>Collected</h3>
                {evolutions.filter(evolution =>
                    evolution.isCollected>0
                ).map(it => (<div key={it.species.id}>{it.species.identifier}</div>))}
            </div>
            <div onMouseEnter={mouseEnter} onMouseLeave={onMouseLeave}>
                {notCollected.length}
            </div>
        </div>
    );
}