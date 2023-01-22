export interface IProduto {
  id: number;
  estoque?: number | null;
  estoqueMinimo?: number | null;
  valorIdeal?: number | null;
  tipo?: string | null;
}

export type NewProduto = Omit<IProduto, 'id'> & { id: null };
