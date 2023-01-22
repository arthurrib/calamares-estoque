export interface IFornecedor {
  id: number;
  nome?: string | null;
  telefone?: string | null;
}

export type NewFornecedor = Omit<IFornecedor, 'id'> & { id: null };
